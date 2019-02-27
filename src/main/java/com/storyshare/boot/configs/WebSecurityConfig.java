package com.storyshare.boot.configs;

import com.storyshare.boot.services.auth.AuthFailureHandler;
import com.storyshare.boot.services.auth.AuthSuccessHandler;
import com.storyshare.boot.services.auth.SuccessLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthFailureHandler();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthSuccessHandler();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SuccessLogoutHandler();
    }
}
