/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityauth.controller;

import javax.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nathan
 */

@Controller
public class HelloController {
    
    @RequestMapping("/")
    public String welcome(){
        return "welcome";
    }
    
    @RequestMapping("/loginpage")
    public String loginpage(){
        return "loginpage";
    }    
            
    @RequestMapping("/fail")
    @ResponseBody
    public String fail(){
        return "fail";
    }
    
    @RequestMapping("/adminpage")
    @ResponseBody
    public String adminpage(){
        return "adminpage";
    }
    
    @RequestMapping("/managerpage")
    @ResponseBody
    public String managerpage(){
        return "managerpage";
    }
    
    @RequestMapping("/employeepage")
    @ResponseBody
    public String employeepage(){
        return "employeepage";
    }
    
    @RequestMapping("/login-error")
    public String login(HttpSession session, Model model) {
        System.out.println(session != null);
        
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "loginpage";
    }
    
    
    
    
    
}
