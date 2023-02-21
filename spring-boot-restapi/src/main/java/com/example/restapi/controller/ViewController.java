/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restapi.controller;

import com.example.restapi.model.Shirt;
import com.example.restapi.service.ShirtService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Nathan
 */
@Controller
public class ViewController {
    @Autowired
    ShirtService shirtService;

    
    @GetMapping("/index")
    public String homepage(Model m){
        List<Shirt> shirtList= (List<Shirt>)shirtService.findAll();
        m.addAttribute("shirtList", shirtList);        
        return "index";
    }
    
}
