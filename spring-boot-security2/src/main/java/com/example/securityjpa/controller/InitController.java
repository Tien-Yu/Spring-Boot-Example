/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.Gender;
import com.example.securityjpa.service.UsersService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private UsersService usersService;

    @GetMapping("/findAll")
    public List<Users> findAll() {
        return (List) usersService.findAll();
    }

    @GetMapping("/search")
    public Users findByUsername(String username) {
        System.out.println(username);
        Optional<Users> tmpUser = usersService.findByUsername(username);
        //500 error code handle not resolved
        return tmpUser.get();
    }

    @GetMapping("/init")
    public List<Users> InitUsers() {
        BCryptPasswordEncoder bp = new BCryptPasswordEncoder();

        if (usersService.findByUsername("Timothy").isEmpty()) {
            System.out.println("init");

            Users manager = new Users();
            manager.setUsername("Timothy");
            manager.setPassword(bp.encode("timothy"));
            manager.setGender(Gender.MALE);
            manager.setAuthority(Arrays.asList("admin", "normal", "ROLE_manager"));

            Users employee1 = new Users();
            employee1.setUsername("Tony");
            employee1.setPassword(bp.encode("tony"));
            employee1.setGender(Gender.MALE);
            employee1.setAuthority(Arrays.asList("normal", "ROLE_employee"));

            Users employee2 = new Users();
            employee2.setUsername("Jacky");
            employee2.setPassword(bp.encode("jacky"));
            employee2.setGender(Gender.MALE);
            employee2.setAuthority(Arrays.asList("normal", "ROLE_employee"));

            Users employee3 = new Users();
            employee3.setUsername("Karen");
            employee3.setPassword(bp.encode("karen"));
            employee3.setGender(Gender.FEMALE);
            employee3.setAuthority(Arrays.asList("normal", "ROLE_employee"));

            usersService.save(manager);
            usersService.save(employee1);
            usersService.save(employee2);
            usersService.save(employee3);
        }
        return findAll();
    }

    @GetMapping("/addTestUsers")
    public List<Users> forDeletion() {
        if (usersService.findByUsername("Leon").isPresent()
                || usersService.findByUsername("Ada").isPresent()) {
            System.out.println("Users already exists!");
            return findAll();
        }
        BCryptPasswordEncoder bp = new BCryptPasswordEncoder();
        System.out.println("Add Users for deletion");
        Users d1 = new Users();
        d1.setUsername("Leon");
        d1.setPassword(bp.encode("Leon"));
        d1.setGender(Gender.MALE);
        d1.setAuthority(Arrays.asList("normal", "ROLE_employee"));

        Users d2 = new Users();
        d2.setUsername("Ada");
        d2.setPassword(bp.encode("ada"));
        d2.setGender(Gender.FEMALE);
        d2.setAuthority(Arrays.asList("normal", "ROLE_employee"));
        usersService.save(d1);
        usersService.save(d2);

        return findAll();
    }

}
