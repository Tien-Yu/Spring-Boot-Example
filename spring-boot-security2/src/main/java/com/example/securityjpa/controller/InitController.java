/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.Gender;
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
    private UsersService usersService;

    @GetMapping("/findAll")
    public String findAll() {
        JSON.DEFAULT_GENERATE_FEATURE &= ~SerializerFeature.SortField.getMask();       
        return JSON.toJSONString(
                (List) usersService.findAllByOrderByUidAsc(), new SerializeConfig(true), SerializerFeature.PrettyFormat);
    }

    @GetMapping("/search")
    public String findByUsername(String username) {
        System.out.println(username);        
        Users user = usersService.findByUsername(username).get();          
        //500 error code handle not resolved
        JSON.DEFAULT_GENERATE_FEATURE &= ~SerializerFeature.SortField.getMask();
        return JSON.toJSONString(user, new SerializeConfig(true), SerializerFeature.PrettyFormat);
    }

    @GetMapping("/init")
    public String InitUsers() {
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
}
