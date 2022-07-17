/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.model;

import com.example.securityjpa.model.support.Gender;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author Nathan
 */
@Data
@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authority;

}
