/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.restapi.repository;

import com.example.restapi.model.Shirt;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Nathan
 */
public interface ShirtRepository extends CrudRepository<Shirt, Integer> {

    public Optional<Shirt> findByName(String name);
    
}
