/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.service;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.repository.UsersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public Iterable<Users> findAll(){
        return usersRepository.findAll();
    }
    public List<Users> findAllByOrderByUidAsc(){
        return usersRepository.findAllByOrderByUidAsc();
    }
    
    
    public Optional<Users> findById(Integer id){
        return usersRepository.findById(id);
    }
    
    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public Users save(Users users) {
        return usersRepository.save(users);
    }

}
