package com.uchechukwu.store.config;

import com.uchechukwu.store.customCsrfToken.CustomCsrfTokenRepository;
import com.uchechukwu.store.customCsrfToken.CustomSpaCsrfTokenRequestHandler;
import com.uchechukwu.store.enums.UserRole;
import com.uchechukwu.store.securityFilters.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomCsrfTokenRepository customCsrfTokenRepository;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                userDetailsService);

        provider.setPasswordEncoder(
                passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                .csrf(csrf -> csrf

                        .csrfTokenRepository(customCsrfTokenRepository)
                        .csrfTokenRequestHandler(
                                new CustomSpaCsrfTokenRequestHandler()
                        )

                        .ignoringRequestMatchers(
                                "/api/v1/webhook/**", "/v3/api-docs/**",
                                "/swagger-ui/**", "/", "/swagger-ui.html", "/templates/**", "/api/v1/webhook/**"))
//                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/api/v1/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/csrf")
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/products/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/cart/create",
                                "/api/v1/cart/{cartId}/item/add")
                        .permitAll()


                        .requestMatchers(
                                "/api/v1/admin/**")
                        .hasRole(UserRole.ADMIN.name())

                        .requestMatchers(
                                "/hello")
                        .hasAnyRole(
                                UserRole.ADMIN.name(),
                                UserRole.USER.name())

                        .anyRequest()
                        .authenticated())

                .authenticationProvider(
                        authenticationProvider())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(exception -> {

                    exception.authenticationEntryPoint(
                            new HttpStatusEntryPoint(
                                    HttpStatus.UNAUTHORIZED));

                    exception.accessDeniedHandler(
                            (request, response, ex) -> {

                                response.setStatus(
                                        HttpStatus.FORBIDDEN.value());

                                response.setContentType(
                                        "application/json");

                                var message = ex.getMessage() != null &&
                                        ex.getMessage()
                                                .toLowerCase()
                                                .contains("csrf")
                                        ? "CsrfToken Required"
                                        : "Access Denied";

                                response.getWriter().write(
                                        """
                                                {
                                                    "success": false,
                                                    "message": "%s"
                                                }
                                                """.formatted(message));
                            });
                })

                .httpBasic(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}