/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.service.UsersService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nathan
 */
@RestController
@RequestMapping("/api")
public class InitController {

    @Autowired
    UsersService usersService;

    @GetMapping("/findAll")
    public List<Users> findAll() {
        return (List) usersService.findAll();
    }

    @GetMapping("/init")
    public String InitUsers() {
        BCryptPasswordEncoder bp = new BCryptPasswordEncoder();
        
        if (usersService.findByUsername("Timothy").isEmpty()) {            
            System.out.println("init");
            
            Users manager = new Users();
            manager.setUsername("Timothy");
            manager.setPassword(bp.encode("timothy"));
            manager.setAuthority(Arrays.asList("admin", "normal", "ROLE_manager"));

            Users employee = new Users();
            employee.setUsername("Tony");
            employee.setPassword(bp.encode("tony"));
            employee.setAuthority(Arrays.asList("normal", "ROLE_employee"));
            
            usersService.save(manager);
            usersService.save(employee);
        }

        return "redirect:/api/findAll";
    }

}
