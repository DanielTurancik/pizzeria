// FILE: src/main/java/sk/pizzeria/demo/security/SecurityConfig.java
package sk.pizzeria.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/pizze", "/pizza/**", "/login", "/register", "/403", "/css/**", "/js/**", "/images/**")
                        .permitAll()

                        .requestMatchers("/cart/**").authenticated()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/kuchar/orders/**").hasAnyRole("COOK", "ADMIN")
                        .requestMatchers("/kurier/orders/**").hasAnyRole("COURIER", "ADMIN")

                        .requestMatchers("/kuchar/**").hasAnyRole("COOK", "ADMIN")
                        .requestMatchers("/kurier/**").hasAnyRole("COURIER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/?logout")
                )
                .exceptionHandling(eh -> eh.accessDeniedPage("/403"))
                .build();
    }
}
