package com.app.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration // this posted spring that this is a config class
@EnableWebSecurity // this enables web security
@EnableMethodSecurity // allow us to use annotations when adding functions

public class SecurityConfig {

    // this object works with the authenticationManager method
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    /**
     * Security filter chain without using annotations
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                // we use this when we're going to access using user & password
//                .httpBasic(Customizer.withDefaults())
//                // we use STATELESS when we're not going to work with sessions but tokens
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                // this is for set the endpoints security
//                .authorizeHttpRequests(http -> {
//                    // endpoints free to access
//                    http.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();
//                    // this allows only user with that authority
//                    http.requestMatchers(HttpMethod.GET, "/auth/hello-secured").hasAuthority("CREATE");
//                    // deny all request different that previous
//                    http.anyRequest().denyAll();
//                })
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // we use this when we're going to access using user & password
                .httpBasic(Customizer.withDefaults())
                // we use STATELESS when we're not going to work with sessions but tokens
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     * Authentication Manager
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    /**
     * Authentication Provider
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    /**
     * User Details Service
     * @return
     */

    @Bean
    public UserDetailsService userDetailsService() {
        // this is the object we use for reading the users credentials
        List<UserDetails> userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("santiago")
                .password("1234")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());

        userDetailsList.add(User.withUsername("wilder")
                .password("123")
                .roles("USER")
                .authorities("READ")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    /**
     * Password Encoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // this one encrypt
        // new BCryptPasswordEncoder();

        // this one don't encrypt & is deprecated
        return NoOpPasswordEncoder.getInstance();

    }
}
