/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.handle;

import com.example.securityjpa.listener.ActiveUserStore;
import com.example.securityjpa.listener.LoggedUser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nathan
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    ActiveUserStore activeUserStore;
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        System.out.println(authentication.getPrincipal().toString());
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser(username, activeUserStore, sessionRegistry);
            session.setAttribute("user", user);
        }
        response.sendRedirect("/employee/" + username);
    }

}
