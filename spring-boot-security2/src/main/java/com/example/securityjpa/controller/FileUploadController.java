/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.controller;

import com.example.securityjpa.model.Users;
import com.example.securityjpa.service.UsersService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nathan
 */
@Controller
public class FileUploadController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> image_upload(@RequestParam("croppedImage") MultipartFile file, Authentication auth, HttpSession session) throws IOException {
        String auth_user = auth.getName();
        String fileName = file.getOriginalFilename();

        Users users = usersService.findByUsername(auth_user).get();
        users.setPhoto(fileName);
        Users savedUsers = usersService.save(users);
        // "/profile-image/" -> will directed to the root where the project at -> D:/
        // add a . will be directed to the current project root
        String uploadDir = "./profile-image/" + savedUsers.getUid();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
/*
        try (InputStream inputStream = file.getInputStream();) {
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            throw new IOException("Could not save uploaded file: "+fileName);
        }
*/
        try {
            file.transferTo(uploadPath.resolve(fileName));          
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("File Uploaded successfully.");
    }

}
