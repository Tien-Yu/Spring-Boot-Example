/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.securityjpa.repository;

import com.example.securityjpa.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nathan
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Optional<Users> findByUsername(String username);
    
    public List<Users> findAllByOrderByUidAsc();
}
