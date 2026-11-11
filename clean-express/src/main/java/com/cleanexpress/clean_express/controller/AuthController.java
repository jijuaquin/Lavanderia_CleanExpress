package com.cleanexpress.clean_express.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Muestra la página de login
    @GetMapping({"/", "/login"})
    public String mostrarLogin() {
        return "login";
    }

    // Procesa las credenciales del personal de recepción
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        // Credenciales fijas para la demostración del mostrador
        if ("recepcion".equals(usuario) && "clean123".equals(password)) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/recepcion";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}