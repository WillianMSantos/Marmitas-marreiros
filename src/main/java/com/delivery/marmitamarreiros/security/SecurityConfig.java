package com.delivery.marmitamarreiros.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests().antMatchers("/auth/register", "/auth/register/**")
                .permitAll().and().httpBasic();
    }
}
