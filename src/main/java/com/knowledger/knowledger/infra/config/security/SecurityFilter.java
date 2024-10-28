package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro de segurança personalizado que intercepta requisições HTTP para realizar autenticação via JWT.
 * Este filtro verifica o token JWT presente no cabeçalho Authorization da requisição, validando e autenticando
 * o usuário com base no conteúdo do token. Caso o token seja válido, ele configura o contexto de segurança
 * com as credenciais do usuário autenticado, permitindo o acesso a recursos protegidos.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService _tokenService;
    private final IUserRepository _iUserRepository;

    /**
     * Construtor da classe SecurityFilter.
     *
     * @param tokenService   instância do serviço responsável pela geração e validação de tokens JWT.
     * @param userRepository repositório de usuários para recuperação de dados do usuário a partir do token.
     */
    public SecurityFilter(TokenService tokenService, IUserRepository userRepository) {
        _tokenService = tokenService;
        _iUserRepository = userRepository;
    }

    /**
     * Executa o processo de filtragem de cada requisição, verificando a presença e validade de um token JWT
     * no cabeçalho Authorization. Se o token for válido, o usuário é autenticado e autorizado a prosseguir.
     *
     * @param request     a requisição HTTP recebida pelo servidor.
     * @param response    a resposta HTTP que será enviada ao cliente.
     * @param filterChain a cadeia de filtros configurados no servidor.
     * @throws ServletException em caso de erro de processamento do servlet.
     * @throws IOException      em caso de erro de entrada/saída.
     */
    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = recoverToken(request);
        if (token != null) {
            authenticateToken(token);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho Authorization da requisição, removendo o prefixo "Bearer ".
     * Utilizamos {@code substring(BEARER_PREFIX.length())} ao invés de {@code replace("Bearer ", "")} por questões
     * de eficiência e segurança, garantindo que o token seja isolado corretamente sem substituir múltiplas ocorrências
     * do prefixo.
     *
     * @param request a requisição HTTP contendo o cabeçalho Authorization com o token JWT.
     * @return o token JWT, se presente e corretamente formatado; caso contrário, retorna null.
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * Autentica o token JWT recuperado, validando-o e definindo o contexto de segurança com as credenciais do usuário.
     * Em caso de falha na verificação do token ou na autenticação do usuário, uma mensagem de aviso é registrada no log.
     *
     * @param token o token JWT a ser validado e autenticado.
     */
    private void authenticateToken(String token) {
        try {
            var email = _tokenService.validateToken(token);
            var role = _tokenService.getRole(token);
            authenticateUser(email, role);
        } catch (JWTVerificationException | BusinessException exception) {
            LOGGER.warn("Falha na autenticação do token: {}", exception.getMessage());
        }
    }

    /**
     * Autentica o usuário com base no e-mail e no papel (role) extraídos do token JWT.
     * Define o contexto de segurança do Spring com as credenciais do usuário autenticado, permitindo acesso a recursos
     * protegidos com base em suas permissões.
     *
     * @param email o e-mail do usuário extraído do token JWT.
     * @param role  o papel do usuário extraído do token JWT, utilizado para definir as permissões de acesso.
     */
    private void authenticateUser(String email, String role) {
        var user = _iUserRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado.", HttpStatus.NOT_FOUND));

        var authority = new SimpleGrantedAuthority(role);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(authority));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}