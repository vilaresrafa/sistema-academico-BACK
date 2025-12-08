package com.apirestful.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtUtil.getEmailFromToken(token);
                System.out.println("[JwtFilter] Request: " + request.getMethod() + " " + request.getRequestURI());
                System.out.println("[JwtFilter] Authorization header present. extracted token (len): " + (token != null ? token.length() : 0));
                System.out.println("[JwtFilter] Extracted username from token: " + username);
            } catch (Exception e) {
                // inválido — segue para entry point
                System.out.println("[JwtFilter] Failed to extract username from token: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean valid = jwtUtil.validateToken(token);
            System.out.println("[JwtFilter] Token validation result: " + valid);
            if (valid) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("[JwtFilter] Authentication set for user: " + userDetails.getUsername() + " authorities=" + userDetails.getAuthorities());
            } else {
                System.out.println("[JwtFilter] Token invalid for user: " + username);
            }
        }

        filterChain.doFilter(request, response);
    }
}
