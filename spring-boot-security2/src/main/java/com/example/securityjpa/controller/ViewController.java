/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nathan
 */
@Controller
public class ViewController {
        
    @GetMapping("/welcome")    
    public String welcome(Authentication authentication){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        String name = authentication.getName();
        System.out.println("Login-user: "+name);        
        return "welcome";
    }
    @GetMapping("/employee/{username}")
    public String userhome(@PathVariable String username, Authentication authentication){
        String authName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!username.equals(authName)){
            return "redirect:/";
        }        
        return "userhome";
    }
    
    
    @GetMapping("/admin")
    @ResponseBody
    public String adminpage(){
        return "ADMIN PAGE!";
    }
    
    @GetMapping("/manager")
    @ResponseBody
    public String managerpage(){
        return "MANAGER PAGE!";
    }
    
    @GetMapping("/employee")
    @ResponseBody
    public String employeepage(){
        return "EMPLOYEE PAGE!";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    
//    
//    @GetMapping("logoutpage")
//    @ResponseBody
//    public String logoutpage(){
//        return "You Have been logout";
//    }
}
