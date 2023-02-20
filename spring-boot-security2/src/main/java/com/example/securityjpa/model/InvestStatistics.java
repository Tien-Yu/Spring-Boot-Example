/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Nathan
 */
@Data
@Entity
public class InvestStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(value = TemporalType.DATE)
    private Date statisticsWhen;

    private String Industry_name;

    private int Accept_TNI;
    private int Accept_KAO;
    private int Accept_QIAO;

    private int Accept_valid_TNI;
    private int Accept_valid_KAO;
    private int Accept_valid_QIAO;

    private int Mass_production_TNI;
    private int Mass_production_KAO;
    private int Mass_production_QIAO;

    private int Under_constructionTNI;
    private int Under_construction_KAO;
    private int Under_construction_QIAO;

}
