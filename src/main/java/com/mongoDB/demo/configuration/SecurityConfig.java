package com.mongoDB.demo.configuration;


import com.mongoDB.demo.service.impl.CustomUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailServiceImpl customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http.cors(r->r.disable())
        .csrf(r->r.disable())
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/login","register").permitAll()
                        .requestMatchers("/members/view").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/members/view/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/members/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(r -> r
                        .loginPage("/login")
                        .defaultSuccessUrl("/members/view",true)
                        .permitAll()
                ).logout(r -> r.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }
}
