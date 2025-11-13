package com.wilker.livraria_api.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Marca a classe como uma fonte de definição de Beans e ativa a segurança web do Spring Security.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Dependência: Utilitário para manipulação de tokens JWT (geração, extração e validação).
    private final JwtUtil jwtUtil;
    // Dependência: Serviço para carregar detalhes do usuário no Spring Security.

    // Construtor usado pelo Spring para injetar as dependências necessárias.
    @Autowired
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Bean principal que configura a cadeia de filtros de segurança HTTP.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cria uma instância do filtro customizado para validação do JWT, injetando as dependências.
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil);

        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF, ideal para APIs RESTful stateless.
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST, "/autor/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/autor/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/autor/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/obra/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/obra/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/obra/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/autor/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/obra/**").hasAnyRole("ADMIN", "USER")

                        // Exige autenticação para qualquer outra requisição não mapeada acima.
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        // Configura a política de criação de sessão para STATELESS, garantindo que não haja
                        // armazenamento de estado da sessão no servidor (ideal para JWT).
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Adiciona o filtro JWT customizado ANTES do filtro padrão de autenticação de usuário/senha.
                // Assim, o JWT é processado primeiro em todas as requisições.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Constrói e retorna o objeto de cadeia de filtros configurado.
        return http.build();
    }

    // Bean que define o algoritmo de criptografia de senha a ser usado (BCrypt).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean que expõe o AuthenticationManager, componente central usado para processar
    // a autenticação de login (geralmente injetado no serviço de autenticação).
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}