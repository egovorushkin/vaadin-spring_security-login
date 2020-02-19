package com.egovorushkin.telrostestapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disables cross-site request forgery (CSRF) protection, as Vaadin already has CSRF protection
        http.csrf().disable()
                // Track unauthorized requests so that users are redirected appropriately after login.
                .requestCache().requestCache(new CustomRequestCache())
                // Turns on authorization.
                .and().authorizeRequests()
                // Allows all internal traffic from the Vaadin framework.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allows all authenticated traffic.
                .anyRequest().authenticated()

                // Enables form-based login and permits unauthenticated access to it.
                .and().formLogin()
                .loginPage("/login").permitAll()
                // Configures the login page URLs.
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                // Configures the logout URL
                .and().logout().logoutSuccessUrl("/login");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}111111")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/frontend/**",
                "/h2-console/**",
                "/frontend-es5/**",
                "/frontend-es6/**");
    }

}
