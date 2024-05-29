package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.headers(
                                headersConfigurer -> headersConfigurer
                                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/","/razas/","/razas/detalle/**","/producto/","/categoria/", "/valoraciones/producto/**", "/registro/nuevo/**","/historia","/motivo","/veterinario","/adopta/", "/contacto/**").permitAll()
                                .requestMatchers("/pedido/**","/lineaPedido/**","/valoraciones/usuario/**","/valoraciones/nuevo/**","/valoraciones/nuevo/**","/valoraciones/borrar/**","/usuario/perfil/editar", "/usuario/perfil/editar/submit")
                                .hasAnyRole("USUARIO","ADMIN")
                                .requestMatchers("/usuario/**","/categorias/**")
                                .hasRole("ADMIN")
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll() // para rutas: /css, /js /images
                                .anyRequest().authenticated())
                                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                                .loginPage("/signin") // mapping par mostrar formulario de login

                                                .loginProcessingUrl("/login") // ruta post de /signin
                                                .usernameParameter("email")
                                                .failureUrl("/signin")
                                                .defaultSuccessUrl("/", true).permitAll())
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/").permitAll())
                                // .csrf(csrf -> csrf.disable())
                                .rememberMe(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/"));
                return http.build();
        }
}
