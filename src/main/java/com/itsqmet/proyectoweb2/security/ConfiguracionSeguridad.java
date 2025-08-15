// Configuración de seguridad
package com.itsqmet.proyectoweb2.security;

import com.itsqmet.proyectoweb2.configuration.UsuarioPrincipal;
import com.itsqmet.proyectoweb2.entity.Cliente;
import com.itsqmet.proyectoweb2.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class ConfiguracionSeguridad {

    @Autowired
    @Lazy
    private ClienteService clienteService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ⚠️ Sin encriptado, solo para desarrollo
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/clientes/guardar").permitAll()
                        .requestMatchers("/empleados/registroEmpleado","/productos").permitAll()
                        .requestMatchers("/pedidos/guardar").permitAll()
                        .requestMatchers("/productos/actualizarStock/**").permitAll()
                        .requestMatchers("/clientes/actualizar/**").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/clientes/**","/pedidos/**").hasRole("CLIENTE")
                        .requestMatchers("/empleados/guardar").hasRole("EMPLEADO")
                        .requestMatchers("/empleados/**").hasRole("EMPLEADO")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .usernameParameter("correoElectronico")
                        .passwordParameter("password")
                        .successHandler((req, res, auth) -> {
                            UsuarioPrincipal user = (UsuarioPrincipal) auth.getPrincipal();
                            String rol = user.getAuthorities().stream()
                                    .findFirst()
                                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                                    .orElse("");

                            Cliente cliente = null;
                            if ("CLIENTE".equals(rol)) {
                                // Obtener cliente real desde BD
                                cliente = clienteService.buscarClientePorId(user.getId()).orElse(null);
                            }

                            res.setContentType("application/json");

                            // Construir JSON
                            StringBuilder json = new StringBuilder();
                            json.append("{");
                            json.append("\"status\":\"ok\",");
                            json.append("\"rol\":\"").append(rol).append("\",");
                            json.append("\"id\":").append(user.getId()).append(",");

                            int suscripcionActiva = 1;
                            String tipoSuscripcion = "premium";

                            if (cliente != null) {
                                suscripcionActiva = cliente.isSuscripcionActiva() ? 1 : 0;
                                tipoSuscripcion = (cliente.getTipoSuscripcion() != null && !cliente.getTipoSuscripcion().isEmpty())
                                        ? cliente.getTipoSuscripcion().toLowerCase()
                                        : "premium"; // fallback si tipo nulo
                            }

                            json.append("\"suscripcion_activa\":").append(suscripcionActiva).append(",");
                            json.append("\"tipo_suscripcion\":\"").append(tipoSuscripcion).append("\"");

                            // Datos adicionales del cliente
                            if (cliente != null) {
                                json.append(",");
                                json.append("\"nombreCompleto\":\"").append(cliente.getNombreCompleto()).append("\",");
                                json.append("\"correoElectronico\":\"").append(cliente.getCorreoElectronico()).append("\"");
                            }

                            json.append("}");
                            res.getWriter().write(json.toString());
                        })
                        .failureHandler((req, res, ex) -> {
                            res.setStatus(401);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"status\":\"error\",\"mensaje\":\"Credenciales incorrectas\"}");
                        })
                )

                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((req, res, auth) -> {
                            res.setStatus(200);
                            res.setContentType("text/plain");
                            res.getWriter().write("Logout exitoso");
                        })
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
