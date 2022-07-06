/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.service;

import com.example.securityjpa.model.Users;
import java.util.Optional;
import java.util.stream.Collectors;
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
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UsersService usersService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: "+username);
        Optional<Users> tmpUser = usersService.findByUsername(username);
        if(!tmpUser.isPresent()){
            throw new UsernameNotFoundException("User not found!");
        }
        Users user = tmpUser.get();
        String password = user.getPassword();
        String authority = user.getAuthority().stream().collect(Collectors.joining(","));       
        return new User(username, 
                        password,                 
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
    }
    
    
}
