package com.example.service;

import com.example.Dto.UserDto;
import com.example.Repo.UserRepo;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public Long createUser(UserDto userDto){
        User user=dtoToUser(userDto);
        user=userRepo.save(user);
        return user.getId();
    }

    private User dtoToUser(UserDto userDto){
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setKycNumber(userDto.getKycNumber());
        return user;
    }
}
