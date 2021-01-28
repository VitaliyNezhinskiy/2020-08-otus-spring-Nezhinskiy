package ru.otus.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/books", "/style.css").permitAll()
                .and()
                .authorizeRequests().antMatchers("/books/add", "/books/*/comments/**").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/books/delete").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/books/*/edit").hasAnyRole("ADMIN", "MANAGER")
                .and()
                .authorizeRequests()
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .and().logout().logoutUrl("/logout");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}