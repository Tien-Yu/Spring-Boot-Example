/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.handle;

import com.example.securityjpa.oauth.CustomOAuth2User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *
 * @author Nathan
 */
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication.getPrincipal() instanceof CustomOAuth2User){            
            System.out.println("logged out user: " + ((CustomOAuth2User) authentication.getPrincipal()).getEmail());
        }else{
            System.out.println("logged out user: " + authentication.getName());        
        }
        
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }
        //SessionInformation -> expire while logout
        sessionRegistry.getAllSessions(authentication.getPrincipal(), false)
                .stream()
                .forEach(i -> i.expireNow());
        
        
        
        response.sendRedirect("/login?logout");

    }

}
