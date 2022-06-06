/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restapi.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Nathan
 */
@Entity
@Data
public class Shirt implements Serializable {   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;    
    @NotNull 
    @NotBlank      
    private String name;   
    @NotNull
    @Min(1)
    private int price;
}
