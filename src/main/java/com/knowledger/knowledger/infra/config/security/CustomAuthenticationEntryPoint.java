package com.knowledger.knowledger.infra.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ponto de entrada personalizado para autenticação, utilizado pelo Spring Security para lidar com tentativas
 * de acesso não autorizadas a recursos protegidos. Este componente fornece uma resposta de erro estruturada
 * em JSON e registra um log sempre que uma tentativa de autenticação falha.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private static final String UNAUTHORIZED_MESSAGE = "Acesso não autorizado.";

    /**
     * Método invocado automaticamente pelo Spring Security sempre que uma tentativa de acesso a um recurso protegido
     * falha por falta de autenticação. Este método configura o código de status HTTP como 401 (Unauthorized),
     * define o tipo de conteúdo da resposta como JSON, e envia uma mensagem de erro estruturada.
     *
     * @param request       a solicitação HTTP recebida pelo servidor.
     * @param response      a resposta HTTP que será enviada ao cliente, contendo o status de erro e a mensagem de erro.
     * @param authException a exceção de autenticação lançada pelo Spring Security, indicando a causa da falha de autenticação.
     * @throws IOException se ocorrer um erro ao escrever a resposta JSON no corpo da resposta HTTP.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        LOGGER.warn("Tentativa de acesso não autorizado: {}", authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, String> data = new HashMap<>();
        data.put("error", UNAUTHORIZED_MESSAGE);

        var out = response.getOutputStream();
        var mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
    }

}