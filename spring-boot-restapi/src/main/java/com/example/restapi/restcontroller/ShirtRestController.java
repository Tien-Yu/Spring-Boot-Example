/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restapi.restcontroller;

import com.example.restapi.model.Order;
import com.example.restapi.model.Shirt;
import com.example.restapi.model.tmpObj;
import com.example.restapi.service.ShirtService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nathan
 */
@RestController
@RequestMapping("/api")
public class ShirtRestController {

    @Autowired
    ShirtService shirtService;

    @GetMapping("/findAll")
    public Iterable<Shirt> findAllShirt() {
        return shirtService.findAll();
    }

    @GetMapping("/delete/{name}")
    public Integer deleteByName(@PathVariable("name") String name, Model m) {
        Optional<Shirt> shirt = shirtService.findByName(name);
        if (!shirt.isPresent()) {
            throw new NoSuchElementException("can't find shirt");
        }
        return shirtService.delete(shirt.get());
    }

    @PostMapping("/add")
    public Shirt add(@RequestBody Shirt shirt) {
        shirtService.add(shirt);
        return shirtService.findByName(shirt.getName()).get();
    }


    @GetMapping("/confirm")
    public List<Shirt> confirm(String name, Integer amount) {
        Optional<Shirt> shirt = shirtService.findByName(name);
        List<Shirt> shirtList = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            shirtList.add(shirt.get());
        }
        return shirtList;
    }

    @PostMapping("/submit")
    public void submit(@RequestBody List<tmpObj> mylist, HttpSession session) {
        
        List<Order> orderList = (List<Order>)session.getAttribute("orderList");
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        
        int rand = ThreadLocalRandom.current().nextInt(5000000);

        for (tmpObj obj : mylist) {
            Shirt shirt = shirtService.findById(obj.getId());
            String size = obj.getSize();
            Order order = new Order();
            order.setId(rand);
            order.setShirt(shirt);
            order.setSize(size);
            orderList.add(order);
        }
        session.setAttribute("orderList", orderList);
        
    }

}
