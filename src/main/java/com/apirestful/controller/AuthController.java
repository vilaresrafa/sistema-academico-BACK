package com.apirestful.controller;

import com.apirestful.dto.AuthRequest;
import com.apirestful.dto.RegisterRequest;
import com.apirestful.model.Usuario;
import com.apirestful.security.UserDetailsImpl;
import com.apirestful.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /** LOGIN */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getSenha());
        return Map.of("token", token);
    }

    /** REGISTRO — público */
    @PostMapping("/register")
    public Usuario register(@RequestBody @Valid RegisterRequest request) {
        return authService.registrar(request.getNome(), request.getEmail(), request.getSenha(), request.getRole());
    }

    /** CRIAR USUÁRIO — apenas ADMIN */
    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario registerAdmin(@RequestBody @Valid RegisterRequest request, Authentication authentication) {
        return authService.registrarPorAdmin(request.getNome(), request.getEmail(), request.getSenha(), request.getRole(), authentication);
    }

    /** PERFIL DO USUÁRIO AUTENTICADO */
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("Usuário não autenticado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList();

        Map<String, Object> resp = new HashMap<>();
        resp.put("email", userDetails.getUsername());
        resp.put("nome", userDetails.getUsuario().getEmail()); // ajustar se tiver nome real
        resp.put("roles", roles);

        return resp;
    }
}
