/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.securityjpa.model.support;

import com.example.securityjpa.model.Users;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author Nathan
 */

@Data
public class UsersForm {  
    private String nickname;
    private Gender gender;
    private String phone;
//    private String description;

    public Users convertToUsers(Users users) throws IllegalAccessException{
        return new UsersFormConverter().convertTo(this, users);
    }
    
    private class UsersFormConverter{
        public Users convertTo(UsersForm usersForm, Users users) throws IllegalAccessException{
            Class c = usersForm.getClass();
            Field[] fields = c.getDeclaredFields();
            Set<String> ignorePropertiesSet = new HashSet<>();            
            for(Field field : fields){
                field.setAccessible(true);
                if(field.get(usersForm).equals("")){
                    ignorePropertiesSet.add(field.getName());
                }
            }
            String[] ignoreProperties = ignorePropertiesSet.toArray(String[]::new);
            
            BeanUtils.copyProperties(usersForm, users, ignoreProperties);            
            return users;            
        }        
    }
}
