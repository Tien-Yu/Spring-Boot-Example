/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.Gender;
import com.example.securityjpa.model.support.MessageMapKeys;
import com.example.securityjpa.service.UsersService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsersService usersService;

    @GetMapping("")
    public String admin_mgmt(Model m) {
        List<Users> expiredList = usersService.findAllByAccountNonExpriedByOrderByUidAsc(false);
        m.addAttribute("expiredList", expiredList);
        return "admin_mgmt";
    }

    //Test action 1
    //revert expired user (remove key in messageMap) GET or POST
    public String revert_expired(String username) {
        Users user = usersService.findByUsername(username).get();
        user.setAccountNonExpired(true);
        user.getMessageMap().remove(MessageMapKeys.ACCOUNT_EXPIRED_MSG);
        usersService.save(user);  //does it really remove message in the database

        return "redirect:/admin";
    }

    //Test action 2
    //delete expired user (test cascade with message) GET or POST
    public String delete_expired(String username) {
        Users user = usersService.findByUsername(username).get();
        usersService.delete(user);

        return "redirect:/admin";
    }

    @GetMapping("/addTestUsers")
    public String addTestUsers() {
        if (usersService.findByUsername("Leon").isPresent()
                || usersService.findByUsername("Ada").isPresent()) {
            System.out.println("Users already exists!");
            return "redirect:/api/findAll";
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

        return "redirect:/api/findAll";
    }

}
