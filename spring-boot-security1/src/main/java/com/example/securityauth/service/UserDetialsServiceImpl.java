/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityauth.service;

import com.example.securityauth.repository.UserRepository;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service//("userDetailsService")
public class UserDetialsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: "+username);

        //1. find user
        Optional<Entry<String, Map<String, String>>> opt = userRepository.users.entrySet().stream()
                .filter(e -> e.getKey().equals(username))
                .findFirst();
        
        if(!opt.isPresent()){
            throw new UsernameNotFoundException("Not found!");
        }
        
        //2. get password and matching
        Map<String, String> info = opt.get().getValue();
        String password = info.get("password");
        String authority = info.get("authority");
        return new User(username, 
                        password, 
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
    }
    
    
    
}
