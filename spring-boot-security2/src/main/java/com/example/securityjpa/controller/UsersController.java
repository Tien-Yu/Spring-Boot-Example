/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.listener.ActiveUserStore;
import com.example.securityjpa.model.Message;
import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.MessageMapKeys;
import com.example.securityjpa.model.support.UsersForm;
import com.example.securityjpa.service.UsersService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Autowired
    ActiveUserStore activeUserStore;
    @Autowired
    SessionRegistry sessionRegistry;

    @PostMapping("/update")
    public String usersUpdate(int uid, UsersForm usersForm, Authentication auth) {
        Users users = usersService.findById(uid).get();
        System.out.println("Input:");
        System.out.println("Nickname -> " + usersForm.getNickname());
        System.out.println("Gender -> " + usersForm.getGender());
        System.out.println("Contract -> " + usersForm.getPhone());
        System.out.println();
        try {
            users = usersForm.convertToUsers(users);
            usersService.save(users);
        } catch (IllegalAccessException ex) {
            ex.getMessage();
        }
        System.out.println("Updated Information:");
        System.out.println("Account -> " + users.getUsername());
        System.out.println("Nickname -> " + users.getNickname());
        System.out.println("Gender -> " + users.getGender());
        System.out.println("Contract -> " + users.getPhone());

        return "redirect:/user/profile";
    }

    @PostMapping("/expiration")
    public String test(String username, Authentication authentication, HttpServletRequest request) {
        Users user = usersService.findByUsername(username).get();
        Map<String, Message> messageMap = user.getMessageMap();        

        Message msg = messageMap.getOrDefault(MessageMapKeys.ACCOUNT_EXPIRED_MSG, new Message());
        msg.setUsers(user);
        msg.setCreatedOn(new Date());
        msg.setAddresser("SYSTEM");
        msg.setMessage("This account has been expired by administrator");               

        if (authentication.getName().equals(username)) {
            try {
                request.logout();
            } catch (ServletException ex) {
                Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            msg.setMessage("This account has been expired by user");
        }        
        messageMap.put(MessageMapKeys.ACCOUNT_EXPIRED_MSG, msg);

        user.setMessageMap(messageMap);
        user.setAccountNonExpired(false);
        usersService.save(user);

        return "redirect:/group/workers";
    }

    @GetMapping("/loggedUsers")
    @ResponseBody
    public List<String> getLoggedUsers() {
        return activeUserStore.getUsers();
    }

    @GetMapping("/sessionRegistry")
    @ResponseBody
    public List<String> getSessionRegistry() {
        //Get All Session Information
        sessionRegistry
                .getAllPrincipals()
                .stream()
                .forEach(principals
                        -> {
                    sessionRegistry
                            .getAllSessions(principals, true)
                            .stream()
                            .forEach(info -> System.out.println("sessionId: " + info.getSessionId() + ", expired: " + info.isExpired()));
                });

        return sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
    }

}
