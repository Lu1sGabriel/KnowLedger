package com.knowledger.knowledger.infra.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança da aplicação utilizando o Spring Security.
 * Define as políticas de acesso aos endpoints, o gerenciamento de sessão como stateless,
 * e configura os filtros de autenticação para uso de JWT.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityFilter _securityFilter;
    private final CustomAuthenticationEntryPoint _authenticationEntryPoint;

    /**
     * Construtor da configuração de segurança.
     *
     * @param securityFilter           filtro de segurança personalizado para autenticação com JWT.
     * @param authenticationEntryPoint ponto de entrada personalizado para tratamento de acessos não autorizados.
     */
    public SecurityConfig(SecurityFilter securityFilter, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        _securityFilter = securityFilter;
        _authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * Configura a cadeia de filtros de segurança da aplicação.
     * Define as permissões de acesso para os endpoints, desabilita a proteção CSRF (por ser uma API stateless),
     * configura o gerenciamento de sessão como stateless, e adiciona o filtro de segurança personalizado antes
     * do filtro de autenticação padrão.
     *
     * @param http o objeto {@link HttpSecurity} utilizado para configurar a segurança HTTP da aplicação.
     * @return o {@link SecurityFilterChain} configurado com as definições de segurança.
     * @throws Exception caso ocorra algum erro durante a configuração da segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users/login", "/users/register").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handler -> handler.authenticationEntryPoint(_authenticationEntryPoint))
                .addFilterBefore(_securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura o gerenciador de autenticação da aplicação, permitindo o acesso ao {@link AuthenticationManager}.
     * O gerenciador de autenticação é responsável por processar as credenciais de autenticação dos usuários.
     *
     * @param authenticationConfiguration a configuração de autenticação do Spring Security, usada para obter o gerenciador de autenticação.
     * @return uma instância de {@link AuthenticationManager} configurada para a aplicação.
     * @throws Exception caso ocorra algum erro ao obter o {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura o codificador de senha utilizado pela aplicação.
     * Utiliza o algoritmo BCrypt para codificar as senhas, garantindo segurança na sua persistência.
     * O BCrypt é amplamente utilizado devido à sua resistência a ataques de força bruta.
     *
     * @return uma instância de {@link PasswordEncoder} configurada com o algoritmo BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}