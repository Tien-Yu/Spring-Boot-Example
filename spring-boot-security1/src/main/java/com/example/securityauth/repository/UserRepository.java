/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityauth.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nathan
 */
@Repository
public class UserRepository {
    public Map<String, Map<String, String>> users;
    {
        //user1, 1234
        Map<String, String> info1 = new LinkedHashMap<>();
        info1.put("password", "$2a$10$cmVBYErdUDPwT65pXLlVTe6gYrlkpO5a92lk5HDaKKFrTeRgivnwO");
        info1.put("authority", "admin, normal, ROLE_manager");
                
        //user2, 5678
        Map<String, String> info2 = new LinkedHashMap<>();
        info2.put("password", "$2a$10$M1xS0KSwfGjKeKstb3b/Euo4vxsUDxAm2yVboXn6.2Hy190k1GQ7m");
        info2.put("authority", "normal, ROLE_employee");
        
        users = new LinkedHashMap<>();
        users.put("user1", info1);
        users.put("user2", info2);
        
        System.out.println(users);
        
    }
    
    
}
