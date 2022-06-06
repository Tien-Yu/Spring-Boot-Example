/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restapi.service;

import com.example.restapi.model.Shirt;
import com.example.restapi.repository.ShirtRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class ShirtService {
    @Autowired
    ShirtRepository shirtRepository;
    
    public Iterable<Shirt> findAll(){
        return shirtRepository.findAll();
    }
    public Optional<Shirt> findByName(String name){
        return shirtRepository.findByName(name);
    }
    public Shirt findById(int id){
        return shirtRepository.findById(id).get();
    }
    
    
    public void add(Shirt s){        
        shirtRepository.save(s);        
    }
    public Integer delete(Shirt s){ 
        shirtRepository.delete(s);
        return s.getId();
       
    }
    
    
}
