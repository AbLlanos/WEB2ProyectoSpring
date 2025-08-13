        package com.itsqmet.proyectoweb2.controller;

        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.CrossOrigin;
        import org.springframework.web.bind.annotation.GetMapping;

        @Controller
        @CrossOrigin(origins = "http://localhost:4200")
        public class LoginController {

            @GetMapping("/postLogin")
            public String redirigirPorRol(Authentication authentication) {
                User usuario = (User) authentication.getPrincipal();

                String role = usuario.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .findFirst()
                        .orElse("");
                if (role.equals("ROLE_EMPLEADO")) {
                    return "";
                }
                return "redirect:/login?error";
            }

        }