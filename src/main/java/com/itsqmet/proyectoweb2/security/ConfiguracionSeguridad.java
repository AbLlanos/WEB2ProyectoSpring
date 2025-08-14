// Configuración de seguridad
package com.itsqmet.proyectoweb2.security;

import com.itsqmet.proyectoweb2.configuration.UsuarioPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    // Bean para codificar passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para exponer AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuración de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/clientes/guardar").permitAll()
                        .requestMatchers("/empleados/registroEmpleado").permitAll()
                        .requestMatchers("/api/login").permitAll()

                        // Endpoints protegidos
                        .requestMatchers("/clientes/**").hasRole("CLIENTE")
                        .requestMatchers("/empleados/guardar").hasRole("EMPLEADO")
                        .requestMatchers("/empleados/**").hasRole("EMPLEADO")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        // Devuelve JSON 401 si no está autenticado en vez de redirigir
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
                            res.setContentType("application/json");
                            res.getWriter().write("{\"status\":\"ok\",\"rol\":\"" + rol + "\",\"id\":" + user.getId() + "}");
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

    // Configuración CORS para Angular
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
