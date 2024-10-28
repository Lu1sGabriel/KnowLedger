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

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private static final String UNAUTHORIZED_MESSAGE = "Acesso não autorizado.";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Log da exceção de autenticação
        LOGGER.warn("Tentativa de acesso não autorizado: {}", authException.getMessage());

        // Definir o status e o tipo de conteúdo da resposta
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // Criar o corpo da resposta com a mensagem de erro
        Map<String, String> data = new HashMap<>();
        data.put("error", UNAUTHORIZED_MESSAGE);

        // Escrever a mensagem de erro no corpo da resposta
        var out = response.getOutputStream();
        var mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
    }

}