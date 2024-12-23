package com.example.demo.schedule;

import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Schedule {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @PostMapping("/create")
    public ResponseEntity created(User user){
        kafkaTemplate.send(topic, user);
        return ResponseEntity.accepted().build();
    }
}
