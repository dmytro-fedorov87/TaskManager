package com.example.taskmanager.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class SecurityConfiguration {
    //private final AuthenticationSuccessHandler authenticationSuccessHandler;

    // public SecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler) {
    //   this.authenticationSuccessHandler = authenticationSuccessHandler;
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers()//("/login.html", "/js/**", "/css/**", "/favicon.ico", "/logout")
                .permitAll()
                .requestMatchers(toH2Console()).permitAll()
                .and().csrf().ignoringRequestMatchers(toH2Console());
        http.headers().frameOptions().disable();
        //.anyRequest()
        //.permitAll()
        //.authenticated()
        //.and()
        //.oauth2Login()
        //.loginPage("/login.html")
        //.successHandler(authenticationSuccessHandler)
        //.and()
        // .logout()
        //.logoutUrl("/logout")
        //.logoutSuccessUrl("/");

        return http.build();
    }
}
