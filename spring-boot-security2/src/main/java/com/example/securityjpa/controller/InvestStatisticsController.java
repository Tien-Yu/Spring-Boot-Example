/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.InvestStatistics;
import com.example.securityjpa.service.InvestStatisticsCatService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/stsp")
public class InvestStatisticsController {
    
    @Autowired
    private InvestStatisticsCatService catService;
    
    @GetMapping("")
    public String index(Model m){
        
        m.addAttribute("iscat", catService.findAllByOrderBySortNoAsc());        
        return "stsp/index";
    }
    
    @GetMapping("/invest_add")
    public String add(Model m, HttpSession session){
        InvestStatistics invest = new InvestStatistics();
        
        m.addAttribute("category", catService.findAllByOrderBySortNoAsc());        
        m.addAttribute("invest", invest);
        
        return "stsp/invest_add";
    }
    @GetMapping("/del_investList")
    public String del(Model m, HttpSession session){
        session.removeAttribute("list");        
        return add(m, session);
    }
    
    
    @PostMapping("/insert")
    public String insert(InvestStatistics inv, HttpSession session){        
        List<InvestStatistics> list = (List)session.getAttribute("list");
        if(list == null){
            list = new ArrayList<>();            
        }    
        list.add(inv);        
        session.setAttribute("list", list);
        return "redirect:invest_add";
                
    }
    
    
    
}
