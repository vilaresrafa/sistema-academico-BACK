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
    public Usuario registrar(String nome, String email, String senha) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario novo = new Usuario();
        novo.setEmail(email);
        novo.setSenha(passwordEncoder.encode(senha));
        novo.setRole(Role.USER);

        return usuarioRepository.save(novo);
    }
}
