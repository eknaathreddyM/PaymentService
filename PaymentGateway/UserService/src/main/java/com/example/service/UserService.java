package com.example.service;

import com.example.Dto.UserCreatedPayload;
import com.example.Dto.UserDto;
import com.example.Repo.UserRepo;
import com.example.entity.User;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${user.created.topic}")
    private String topic;

    public Long createUser(UserDto userDto){
        User user=dtoToUser(userDto);
        user=userRepo.save(user);
        UserCreatedPayload userCreatedPayload=userCreatedPayload(user);
        userCreatedPayload.setRequestId(MDC.get("requestId"));
        kafkaTemplate.send(topic,userCreatedPayload.getEmail(),userCreatedPayload);
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

    private UserCreatedPayload userCreatedPayload(User user){
        UserCreatedPayload userCreatedPayload=new UserCreatedPayload();
        userCreatedPayload.setUserId(user.getId());
        userCreatedPayload.setName(user.getName());
        userCreatedPayload.setEmail(user.getEmail());
        userCreatedPayload.setPhone(user.getPhone());
        return userCreatedPayload;
    }
}
