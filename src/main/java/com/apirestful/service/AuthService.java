package com.apirestful.service;

import com.apirestful.model.Role;
import com.apirestful.model.Usuario;
import com.apirestful.repository.UsuarioRepository;
import com.apirestful.security.JwtUtil;
import com.apirestful.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** LOGIN */
    public String login(String email, String senha) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );

            UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
            return jwtUtil.generateToken(user);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

    /** REGISTRO */
    public Usuario registrar(String nome, String email, String senha, String role) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario novo = new Usuario();
        novo.setEmail(email);
        novo.setSenha(passwordEncoder.encode(senha));
        // Se role for fornecido, usa; senão, padrão é USER
        novo.setRole(role != null && !role.isEmpty() ? Role.valueOf(role.toUpperCase()) : Role.USER);

        return usuarioRepository.save(novo);
    }

    /** REGISTRO POR ADMIN - com validação de permissão */
    public Usuario registrarPorAdmin(String nome, String email, String senha, String role, org.springframework.security.core.Authentication authentication) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        // Validar se quem está criando é ADMIN
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Apenas administradores podem criar usuários com roles customizados");
        }

        Usuario novo = new Usuario();
        novo.setEmail(email);
        novo.setSenha(passwordEncoder.encode(senha));

        // Admin pode definir role livremente
        if (role != null && !role.isEmpty()) {
            try {
                novo.setRole(Role.valueOf(role.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Role inválido. Use: USER ou ADMIN");
            }
        } else {
            novo.setRole(Role.USER);
        }

        return usuarioRepository.save(novo);
    }
}
