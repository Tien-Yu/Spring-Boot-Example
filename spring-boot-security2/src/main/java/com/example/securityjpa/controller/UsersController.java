/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.Gender;
import com.example.securityjpa.model.support.UsersForm;
import com.example.securityjpa.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;
    
    @PostMapping("/update")
    public String usersUpdate(int uid, UsersForm usersForm, Authentication auth){        
        Users users = usersService.findById(uid).get();
        System.out.println("Input:");
        System.out.println("Nickname -> "+usersForm.getNickname());
        System.out.println("Gender -> "+usersForm.getGender());
        System.out.println("Contract -> "+usersForm.getPhone());
        System.out.println();
        try{
            users = usersForm.convertToUsers(users);
            usersService.save(users);
        }catch(IllegalAccessException ex){
            ex.getMessage();
        }
        
        System.out.println("Updated Information:");
        System.out.println("Account -> "+users.getUsername());
        System.out.println("Nickname -> "+users.getNickname());
        System.out.println("Gender -> "+users.getGender());
        System.out.println("Contract -> "+users.getPhone());
        
        return "redirect:/user/profile";
    }
    
}
