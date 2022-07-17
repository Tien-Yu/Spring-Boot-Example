/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.config;

import com.example.securityjpa.handle.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * defaultSuccessUrl -> redirect to the url requested / set boolean alwaysUse true will let the request url always redirect to the same url
         * successForwardUrl -> user login is a post request / if the success url method is not a post mapping it will cause 405 error
         * loginProcessingUrl -> default value is /login
         * loginPage -> custom your own login page         
         */        
        http.formLogin()
            .loginProcessingUrl("/login")
            .loginPage("/login") //set login-page url(index.html)
            .successHandler(getAuthenticationSuccessHandler())
            .failureUrl("/login?failed")
//            .failureForwardUrl("/login?failed")                
            .and()
            .rememberMe()
            .and()
            .logout(logout -> logout
                    .deleteCookies("JSESSIONID") //default logout method will delete JSESSIONID, remember-me                   
                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/logoutpage") if this is activated the login page information block will not showed
            );
 
        //http.csrf().disable();
 
        /**
         * 1. /api/** -> Init user information / if admin(Timothy) has already been created, it will only return user's json data
         * 2. 
         */
        http.authorizeHttpRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/css/**", "/fonts/**", "/js/**", "/img/**").permitAll()
            .antMatchers("/login", "/").permitAll()
            //.antMatchers("/login").permitAll() //set loginpage accessable to anyone(don't have a login-page)
//            .antMatchers("/logoutpage").permitAll()
            .antMatchers("/admin").hasAuthority("admin")
            .antMatchers("/manager").hasRole("manager")
            .antMatchers("/employee").hasAnyRole("manager", "employee")
            .anyRequest().authenticated(); //contains /logout      
    }
    
    /**
     * disable Spring Security default "/logout" page
     * @param web
     * @throws Exception 
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/logout");
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new LoginSuccessHandler();
    }

   
    
    
}
