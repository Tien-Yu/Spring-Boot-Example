/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Nathan
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); //default POST url            
//            .loginProcessingUrl("/login") //set your login post url
//            .successForwardUrl("/");
        
        http.authorizeHttpRequests()
            .antMatchers("/api/**").permitAll()
            //.antMatchers("/login").permitAll() //set loginpage accessable to anyone
            .antMatchers("/admin").hasAuthority("admin")
            .antMatchers("/manager").hasRole("manager")
            .antMatchers("/employee").hasAnyRole("manager", "employee")
            .anyRequest().authenticated();
        
        http.logout().deleteCookies("JSESSIONID")
                     .logoutSuccessUrl("/")
                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        
        http.csrf().disable();
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

   
    
    
}
