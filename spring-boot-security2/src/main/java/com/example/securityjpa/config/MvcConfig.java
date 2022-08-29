/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Nathan
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{

    /*allow client to access the directory in file system*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // access image using : localhost:8080/profile-image
        Path profileImageUploadDir = Paths.get("./profile-image"); 
        // file:///D:/~projectName/profile-image
        String profileImageUploadPath = profileImageUploadDir.toFile().getAbsolutePath(); 
        // easily access files under /profile-image by url /profile-image
        registry.addResourceHandler("/profile-image/**").addResourceLocations("file:/" + profileImageUploadPath + "/");      
    }
    
}
