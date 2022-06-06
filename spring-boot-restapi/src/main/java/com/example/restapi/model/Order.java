/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restapi.model;

import lombok.Data;

/**
 *
 * @author Nathan
 */

@Data
public class Order {
    private int id;    
    private Shirt shirt;
    private String size;
}
