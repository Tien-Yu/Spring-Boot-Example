/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Nathan
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{    
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier(value = "myAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    @Qualifier(value = "loginSuccessHandler")
    private AuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
        
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //self def login page
        http.formLogin()
                //form action (no need to write a controller, it's logic is written by spring security)
                .loginProcessingUrl("/login")
                //login page
                .loginPage("/loginpage")
                //success
                .successHandler(successHandler)                                
                //fail
                .failureForwardUrl("/login-error");
        
        
        //Authorization
        http.authorizeHttpRequests()
                //page that don't need authorization
                .antMatchers("/loginpage").permitAll()
                //page that need admin authority to visit
                .antMatchers("/adminpage").hasAuthority("admin")
                //page that need manager role to visit
                .antMatchers("/managerpage").hasRole("manager")
                //page that can be visit by manager、employee roles
                .antMatchers("employeepage").hasAnyRole("manager", "employee")                
                //the rest of pages can be visit by anyone after authenticated
                .anyRequest().authenticated();       
        
        //logout (don't use it) - it should be made by some sort of logout form
        http.logout().deleteCookies("JSESSIONID")
                     .logoutSuccessUrl("/loginpage")
                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout")); //can be logout by any http method

        //http.csrf().disable(); //close csrf protection
        
        //Exception handle
        http.exceptionHandling()                               
                //servlet
                .accessDeniedHandler(accessDeniedHandler);                
                //or you can .accessDeniedPage("/exceptionpage");
                
        //remember me
        //html side - <input type="checkbox" name="remember-me">
        http.rememberMe()
                .userDetailsService(userDetailsService)  
                .tokenValiditySeconds(60*3); //usually more then default session time out(30min)    
        
    }

    //Password Encoder for Spring Security to manage
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
