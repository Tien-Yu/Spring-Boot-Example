/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.example.securityauth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Nathan
 */
public class TestPasswordEncoder {
    
    public static void main(String[] args) {
        PasswordEncoder pe = new BCryptPasswordEncoder();
            String ecode = pe.encode("1234");
            System.out.println(ecode);
            boolean matches = pe.matches("1234", ecode);            
            System.out.println(matches);
            
            System.out.println();
            
            String ecode2 = pe.encode("5678");
            System.out.println(ecode2);
            boolean matches2 = pe.matches("5678", ecode2);            
            System.out.println(matches2);
            
            //$2a$10$Gih2txdODqf5RZnAmkfHeOVQsLrsopcgu.xdiPQ4eWzlTuy5f6gPW
            //boolean matches3 = pe.matches("1234", "$2a$10$Gih2txdODqf5RZnAmkfHeOVQsLrsopcgu.xdiPQ4eWzlTuy5f6gPW");            
            //System.out.println(matches3); //true
            
    }
    
}
