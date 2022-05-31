package com.delivery.marmitamarreiros.config;

import com.delivery.marmitamarreiros.repository.UsuarioRepository;
import com.delivery.marmitamarreiros.service.AutenticacaoService;
import com.delivery.marmitamarreiros.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    //Configuracoes de autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuracoes de autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/clientes/").authenticated()
                .antMatchers("/api/clientes/*").authenticated()
                .antMatchers("/api/entregas/").authenticated()
                .antMatchers("/api/entregas/*").authenticated()
                .antMatchers("/api/pedidos/*").authenticated()
                .antMatchers("/api/produtos/").authenticated()
                .antMatchers("/api/produtos/*").authenticated()
                .antMatchers("api/auth").permitAll()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configuracoes de recursos estaticos
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

}
