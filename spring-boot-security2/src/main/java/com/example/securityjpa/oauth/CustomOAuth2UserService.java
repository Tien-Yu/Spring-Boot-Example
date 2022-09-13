/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.oauth;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.service.UsersService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsersService usersService;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        System.out.println("ATTR : ");
        user.getAttributes().forEach((k, v) -> System.out.println("K: " + k + ", V: " + v));
        System.out.println("AUTH : ");
        user.getAuthorities().forEach(System.out::println);
        
        Optional<Users> optional = usersService.findByUsernameAndAccountNonExpired(user.getAttribute("email"), true);
        if(!optional.isPresent()){
            throw new UsernameNotFoundException("User not found!");
        }
       
        
        return new CustomOAuth2User(user);
        

    }

}
