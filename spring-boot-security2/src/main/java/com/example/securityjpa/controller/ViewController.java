/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nathan
 */
@Controller
public class ViewController {
        
    @GetMapping("/")    
    public String welcome(){
        return "welcome";
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
}
