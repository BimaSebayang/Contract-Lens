package com.contractlens.service.analyzer.infrastructure;

import jakarta.servlet.DispatcherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableMethodSecurity
@SuppressWarnings("java:S4502")
public class SecurityConfig {

    private static final Logger log =
            LoggerFactory.getLogger(SecurityConfig.class);


    private final AppCredentialFilter credentialFilter;

    private final Environment environment;

    @Value("${spring.whitelist:}")
    private String[] whiteLists;

    public SecurityConfig(AppCredentialFilter credentialFilter, Environment environment, String[] whiteLists) {
        this.credentialFilter = credentialFilter;
        this.environment = environment;
        this.whiteLists = whiteLists;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] matchers = whiteLists;

        log.info("All whiteList endpoints : {}", (Object) matchers);

        String profileBerjalan = environment.getProperty("spring.profiles.active");
        log.info("profile berjalan ==> {}",profileBerjalan);

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        registry -> registry.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR)
                                .permitAll()
                                .requestMatchers(matchers)
                                .permitAll().anyRequest().authenticated())
                .sessionManagement(
                        manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(credentialFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
