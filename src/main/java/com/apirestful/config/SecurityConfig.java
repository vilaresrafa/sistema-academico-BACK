package com.apirestful.config;

import com.apirestful.security.JwtFilter;
import com.apirestful.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /** AuthenticationManager */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    /** Password encoder */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Filter chain */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // permitir registro e login públicos
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        // outras rotas autenticadas
                        .requestMatchers("/alunos/**").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            String header = request.getHeader("Authorization");
                            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                            System.out.println("[SecurityConfig] authenticationEntryPoint triggered for request: " + request.getMethod() + " " + request.getRequestURI());
                            System.out.println("[SecurityConfig] Authorization header present: " + (header != null));
                            System.out.println("[SecurityConfig] SecurityContext authentication: " + auth);
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            String header = request.getHeader("Authorization");
                            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                            System.out.println("[SecurityConfig] accessDeniedHandler triggered for request: " + request.getMethod() + " " + request.getRequestURI());
                            System.out.println("[SecurityConfig] Authorization header present: " + (header != null));
                            System.out.println("[SecurityConfig] SecurityContext authentication: " + auth);
                            if (header == null || !header.startsWith("Bearer ")) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            } else {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                            }
                        })
                )
                .authenticationManager(authenticationManager());

        // adicionar filtro JWT antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // permitir H2 console
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
