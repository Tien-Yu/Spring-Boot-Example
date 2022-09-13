/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.handle;

import com.example.securityjpa.listener.ActiveUserStore;
import com.example.securityjpa.listener.LoggedUser;
import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.AuthenticationProvider;
import com.example.securityjpa.oauth.CustomOAuth2User;
import com.example.securityjpa.service.UsersService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author Nathan
 */
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UsersService usersService;
    @Autowired
    ActiveUserStore activeUserStore;
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println("OAuth2 Login: "+oAuth2User.getEmail());
        
        Optional<Users> loginUser = usersService.findByUsername(oAuth2User.getEmail());
        if (!loginUser.isPresent()) {
            createNewUserAfterOAuth2LoginSuccess(oAuth2User, AuthenticationProvider.GOOGLE);
        } else {
            updateUserInfoAfterOAuth2LoginSuccess();
        }
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser(oAuth2User.getEmail(), activeUserStore, sessionRegistry);
            session.setAttribute("user", user);
        }
        
        
        response.sendRedirect("/user/" + oAuth2User.getName().replace(" ", "_"));       
    }

    public void createNewUserAfterOAuth2LoginSuccess(CustomOAuth2User oAuth2User, AuthenticationProvider provider) {
        Users user = new Users();
        user.setUsername(oAuth2User.getEmail()); //better be unique
        user.setNickname(oAuth2User.getName());
        user.setAuthority(Arrays.asList("ROLE_USER"));        
        user.setAuthProvider(provider);
        usersService.save(user);

    }
    public void updateUserInfoAfterOAuth2LoginSuccess(){
        
    }

}
