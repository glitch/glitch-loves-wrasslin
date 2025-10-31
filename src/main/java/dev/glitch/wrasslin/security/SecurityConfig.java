package dev.glitch.wrasslin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] SWAGGER_PATHS = {
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/admin/videos/**").hasRole("ADMIN")
                    .requestMatchers(SWAGGER_PATHS).permitAll()
                    .requestMatchers("/videos/**").permitAll()
                    .anyRequest().authenticated())
            .formLogin(form -> form.permitAll().defaultSuccessUrl("/swagger-ui.html", true))
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
        public DaoAuthenticationProvider authenticationProvider(AdminUserDetailsService adminUserDetailsService) {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(adminUserDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }
}
