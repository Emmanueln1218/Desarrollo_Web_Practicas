package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrar(@Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "register";
        }

        if (usuarioService.existeUsername(usuario.getUsername())) {
            model.addAttribute("errorUsername", "El nombre de usuario ya existe");
            return "register";
        }

        if (usuarioService.existeEmail(usuario.getEmail())) {
            model.addAttribute("errorEmail", "El email ya está registrado");
            return "register";
        }

        usuarioService.registrarUsuario(usuario);
        redirectAttributes.addFlashAttribute("success", "¡Registro exitoso! Inicia sesión.");
        return "redirect:/login";
    }
}