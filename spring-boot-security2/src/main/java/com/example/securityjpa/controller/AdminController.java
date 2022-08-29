/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.model.support.Gender;
import com.example.securityjpa.model.support.MessageMapKeys;
import com.example.securityjpa.service.UsersService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsersService usersService;

    @GetMapping("")
    public String admin_mgmt(Model m) {
        List<Users> expiredList = usersService.findAllByAccountNonExpriedByOrderByUidAsc(false);
        m.addAttribute("expiredList", expiredList);
        return "admin_mgmt";
    }

    //map object remove() will not persist out of this method
    @PostMapping("revertExpired")
    public String revert_expired(String username) {
        Users user = usersService.findByUsername(username).get();
        user.setAccountNonExpired(true);
        user.getMessageMap().remove(MessageMapKeys.ACCOUNT_EXPIRED_MSG);
        usersService.save(user);   //trigger orphanRemoval    
        System.out.println("Account " + username + " has been revert form expiration!");
        return "redirect:/admin";
    }

    @PostMapping("deleteExpired")
    public String delete_expired(String username) throws IOException {
        Users user = usersService.findByUsername(username).get();

        String dir = "./profile-image/" + user.getUid();
        Path dirPath = Paths.get(dir);
        File file = dirPath.toFile();

        if (Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS)) {
            clearAndDeleteDirectory_alter(file);
            //FileSystemUtils.deleteRecursively(dirPath); //simple way

        }
        usersService.delete(user);
        return "redirect:/admin";
    }

    void clearAndDeleteDirectory(File directoryToBeDeleted) {
        //return null if not directory
        //if directory is empty, allContents length = 0
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                clearAndDeleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    void clearAndDeleteDirectory_alter(File directoryToBeDeleted) {
        for (File file : directoryToBeDeleted.listFiles()) {
            if (file.isDirectory()) {
                clearAndDeleteDirectory_alter(file);
            }
            file.delete();
        }
        directoryToBeDeleted.delete();
    }

    @GetMapping("/addTestUsers")    
    public ResponseEntity<?> addTestUsers() {
        if (usersService.findByUsername("Leon").isPresent()
                || usersService.findByUsername("Ada").isPresent()) {
            System.out.println("Users already exists!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account name Leon & Ada already exists!");
        }
        BCryptPasswordEncoder bp = new BCryptPasswordEncoder();
        System.out.println("Add Users for deletion");
        Users d1 = new Users();
        d1.setUsername("Leon");
        d1.setPassword(bp.encode("leon"));
        d1.setGender(Gender.MALE);
        d1.setAuthority(Arrays.asList("normal", "ROLE_employee"));

        Users d2 = new Users();
        d2.setUsername("Ada");
        d2.setPassword(bp.encode("ada"));
        d2.setGender(Gender.FEMALE);
        d2.setAuthority(Arrays.asList("normal", "ROLE_employee"));
        usersService.save(d1);
        usersService.save(d2);

        return ResponseEntity.ok("User has been add Successfully!");
    }

}
