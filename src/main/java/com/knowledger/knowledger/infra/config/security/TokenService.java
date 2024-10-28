package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Serviço responsável pela geração e validação de tokens JWT para autenticação e autorização de usuários.
 * Este serviço utiliza uma chave secreta para assinar os tokens e define um tempo de expiração para os mesmos.
 */
@Component
public class TokenService {

    private final Algorithm _algorithm;
    private final long _expirationTime;

    private static final String ISSUER = "knowledger";
    private static final String ROLE_CLAIM = "role";
    private static final String EMAIL_CLAIM = "email";

    /**
     * Construtor que inicializa o algoritmo JWT e o tempo de expiração do token.
     * A chave secreta e o tempo de expiração são fornecidos através das propriedades configuradas no ambiente.
     *
     * @param jwtSecret      chave secreta usada para assinar o token JWT.
     * @param expirationTime tempo de expiração do token JWT em milissegundos, a partir do momento de sua criação.
     */
    public TokenService(@Value("${jwt.secret}") String jwtSecret,
                        @Value("${jwt.expiration}") long expirationTime) {
        _algorithm = Algorithm.HMAC512(jwtSecret);
        _expirationTime = expirationTime;
    }

    /**
     * Gera um token JWT assinado com base no e-mail e no papel do usuário.
     * O token gerado contém as informações do e-mail e do papel do usuário como claims, além de um tempo de expiração
     * e um emissor.
     *
     * @param email o e-mail do usuário, usado como o assunto (subject) do token.
     * @param role  o papel do usuário, armazenado como um claim no token para definir suas permissões.
     * @return o token JWT gerado, assinado e pronto para uso em autenticação e autorização.
     */
    public String generateToken(String email, String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(email)
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(ROLE_CLAIM, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + _expirationTime))
                .sign(_algorithm);
    }

    /**
     * Verifica a validade do token JWT fornecido. Este método garante que o token foi emitido pelo emissor correto
     * e foi assinado com a chave secreta apropriada. Caso o token seja inválido, uma exceção é lançada.
     *
     * @param token o token JWT a ser verificado.
     * @return o objeto {@link DecodedJWT} decodificado, contendo as informações do token se ele for válido.
     */
    private DecodedJWT verifyToken(String token) {
        return JWT.require(_algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    /**
     * Valida o token JWT e retorna o e-mail (subject) do usuário.
     * Este método utiliza o token verificado para recuperar o e-mail associado ao usuário autenticado.
     *
     * @param token o token JWT que contém as informações de autenticação do usuário.
     * @return o e-mail do usuário autenticado, extraído do token JWT.
     */
    public String validateToken(String token) {
        return verifyToken(token).getSubject();
    }

    /**
     * Extrai e retorna o papel (role) do usuário a partir do token JWT.
     * O papel é utilizado para determinar as permissões e acessos do usuário no sistema.
     *
     * @param token o token JWT que contém as informações de autorização do usuário.
     * @return o papel do usuário, extraído do claim "role" no token JWT.
     */
    public String getRole(String token) {
        return verifyToken(token).getClaim(ROLE_CLAIM).asString();
    }

}