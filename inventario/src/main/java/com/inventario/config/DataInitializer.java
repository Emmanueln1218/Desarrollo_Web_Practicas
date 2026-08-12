package com.inventario.config;

import com.inventario.model.Usuario;
import com.inventario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Solo crea el usuario si NO existe
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setNombreCompleto("Administrador del Sistema");
            admin.setEmail("admin@inventario.com");
            admin.setRol("ROLE_ADMIN");
            admin.setActivo(true);
            usuarioRepository.save(admin);
            System.out.println("========================================");
            System.out.println("  USUARIO ADMIN CREADO EXITOSAMENTE");
            System.out.println("  Usuario: admin");
            System.out.println("  Contraseña: admin");
            System.out.println("========================================");
        } else {
            // Si ya existe, actualiza la contraseña para asegurar que sea "admin"
            Usuario admin = usuarioRepository.findByUsername("admin").get();
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRol("ROLE_ADMIN");
            admin.setActivo(true);
            usuarioRepository.save(admin);
            System.out.println("========================================");
            System.out.println("  CONTRASEÑA DEL ADMIN ACTUALIZADA");
            System.out.println("  Usuario: admin");
            System.out.println("  Contraseña: admin");
            System.out.println("========================================");
        }
    }
}