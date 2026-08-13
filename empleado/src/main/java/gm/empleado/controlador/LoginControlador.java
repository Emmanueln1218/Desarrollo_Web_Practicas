package gm.empleado.controlador;

import gm.empleado.modelo.Usuario;
import gm.empleado.repositorio.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Muestra la vista de Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // Procesa el formulario de autenticación
    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                ModelMap modelo) {

        Usuario usuario = usuarioRepositorio.findByUsernameAndPassword(username, password);

        if (usuario != null) {
            // Guardamos el usuario en la sesión HTTP
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/";
        } else {
            modelo.put("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    // Cierra la sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Destruye la sesión activa
        return "redirect:/login";
    }
}
