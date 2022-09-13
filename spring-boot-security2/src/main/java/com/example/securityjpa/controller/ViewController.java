/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.oauth.CustomOAuth2User;
import com.example.securityjpa.service.UsersService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nathan
 */
@Controller
public class ViewController {

    @Autowired
    UsersService usersService;

    @GetMapping("/employee/{username}")
    public String userhome(@PathVariable String username, Authentication authentication) {
        String authName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(authName)) {
            return "redirect:/";
        }
        return "userhome";
    }

    @GetMapping("/user/{username}")
    public String _userhome(@PathVariable String username, Authentication authentication) {
//        String authName = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomOAuth2User oAuthUser = (CustomOAuth2User)authentication.getPrincipal();        
        String oAuthUserName = oAuthUser.getName().replace(" ", "_");
        
        if(!username.equals(oAuthUserName)){
            return "redirect:/";
        }
        return "userhome";        
    }

    @GetMapping("/user/profile")
    public String user_profile(Model m, Authentication auth) {

        if (auth.getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User oAuthUser = (CustomOAuth2User) auth.getPrincipal();
            Users users = usersService.findByUsername(oAuthUser.getEmail()).get();
            m.addAttribute("users", users);
            return "user_profile";
        }
        Users users = usersService.findByUsername(auth.getName()).get();
        m.addAttribute("users", users);
        return "user_profile";
    }

    @GetMapping("/group/workers")
    public String workers(Model m) {
        List<Users> usersList = usersService.findAllByAccountNonExpriedByOrderByUidAsc(true);
        List<Users> admin = usersList.stream().filter(o -> o.getRole().equals("manager")).collect(Collectors.toList());
        List<Users> normal = usersList.stream().filter(o -> o.getRole().equals("employee")).collect(Collectors.toList());
        m.addAttribute("admin", admin);
        m.addAttribute("normal", normal);
        return "workers";
    }
    @GetMapping("/group/others")
    public String other(Model m) {
        List<Users> usersList = usersService.findAllByAccountNonExpriedByOrderByUidAsc(true);
        List<Users> others = usersList.stream().filter(o -> o.getRole().equals("USER")).collect(Collectors.toList());       
        m.addAttribute("others", others);        
        return "others";
    }
    

    @GetMapping("/manager")
    @ResponseBody
    public String managerpage() {
        return "MANAGER PAGE!";
    }

    @GetMapping("/employee")
    @ResponseBody
    public String employeepage() {
        return "EMPLOYEE PAGE!";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
