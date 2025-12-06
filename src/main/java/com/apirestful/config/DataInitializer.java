package com.apirestful.config;

import com.apirestful.model.Role;
import com.apirestful.model.Usuario;
import com.apirestful.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() == 0) {

            // ADMIN
            Usuario admin = new Usuario();
            admin.setEmail("admin@admin.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN); // << IMPORT CORRETO
            usuarioRepository.save(admin);

            // USUÁRIO COMUM
            Usuario user = new Usuario();
            user.setEmail("user@user.com");
            user.setSenha(passwordEncoder.encode("user123"));
            user.setRole(Role.USER); // << IMPORT CORRETO
            usuarioRepository.save(user);

            System.out.println("👉 Usuários padrão criados: admin e user");
        }
    }
}
