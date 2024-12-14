package com.example.Cotroller;

import com.example.Dto.UserDto;
import com.example.UserApp;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
public class UserController {

   @Autowired
   private UserService userService;

   @GetMapping
   public String welocome(){
       return "user-service";
   }
    @PostMapping("/user")
    public Long createUser(@RequestBody @Valid UserDto userdto){
        return userService.createUser(userdto);
    }
}
