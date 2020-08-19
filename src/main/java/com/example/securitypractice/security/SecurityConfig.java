package com.example.securitypractice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.securitypractice.security.UserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index").permitAll()
                .antMatchers("/api/**").hasRole(ACCOUNTANT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails michaelScott = User.builder()
                .username("michaelscott")
                .password(passwordEncoder.encode("password"))
                .authorities(MANAGER.getGrantedAuthorities())
                .build();

        UserDetails dwightSchrute = User.builder()
                .username("dwightschrute")
                .password(passwordEncoder.encode("password"))
                .authorities(SALESMAN.getGrantedAuthorities())
                .build();

        UserDetails kevinMalone = User.builder()
                .username("kevinmalone")
                .password(passwordEncoder.encode("password"))
                .authorities(ACCOUNTANT.getGrantedAuthorities())
                .build();


        return new InMemoryUserDetailsManager(
                michaelScott,
                dwightSchrute,
                kevinMalone
        );
    }
}
