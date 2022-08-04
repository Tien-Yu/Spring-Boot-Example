/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.listener;

import java.util.List;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.springframework.security.core.session.SessionRegistry;

/**
 * Listener of HttpSession The session binds the object by a call to
 * HttpSession.setAttribute and unbinds the object by a call to
 * HttpSession.removeAttribute.
 *
 * @author Nathan
 */
public class LoggedUser implements HttpSessionBindingListener {

    private String username;
    private ActiveUserStore activeUserStore;
    private SessionRegistry sessionRegistry;

    public LoggedUser(String username, ActiveUserStore activeUserStore, SessionRegistry sessionRegistry) {
        this.username = username;
        this.activeUserStore = activeUserStore;
        this.sessionRegistry = sessionRegistry;
    }

    public LoggedUser() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();

        if (!users.contains(user.getUsername())) {
            users.add(user.getUsername());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        //expire or remove
        sessionRegistry.removeSessionInformation(event.getSession().getId());
        System.out.println("SessionInformation removed! "+event.getSession().getId());

        if (users.contains(user.getUsername())) {
            users.remove(user.getUsername());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActiveUserStore getActiveUserStore() {
        return activeUserStore;
    }

    public void setActiveUserStore(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

}
