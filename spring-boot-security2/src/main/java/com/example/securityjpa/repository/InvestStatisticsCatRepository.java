/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.securityjpa.repository;

import com.example.securityjpa.model.InvestStatisticsCat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nathan
 */
public interface InvestStatisticsCatRepository extends JpaRepository<InvestStatisticsCat, Integer>{
    List<InvestStatisticsCat> findAllByOrderBySortNoAsc();
}
