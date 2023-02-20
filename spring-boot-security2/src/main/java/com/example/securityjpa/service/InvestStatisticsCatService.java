/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.service;

import com.example.securityjpa.model.InvestStatisticsCat;
import com.example.securityjpa.repository.InvestStatisticsCatRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class InvestStatisticsCatService {
    @Autowired
    private InvestStatisticsCatRepository iscr;
    
    public List<InvestStatisticsCat> findAllByOrderBySortNoAsc(){
        return iscr.findAllByOrderBySortNoAsc();
    }
    
}
