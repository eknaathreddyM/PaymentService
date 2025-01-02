package com.example.controller;

import com.example.UserCreatedPayload;
import com.example.WalletUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class KafkaListner {


    private static Logger logger=LoggerFactory.getLogger(KafkaListner.class);
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private  SimpleMailMessage simpleMailMessage;
    @Autowired
    private JavaMailSender javaMailSender;


    @KafkaListener(topics = "${user.created.topic}", groupId = "user")
    public void consume(ConsumerRecord payload) throws JsonProcessingException {
        logger.info(payload.toString());
        UserCreatedPayload userCreatedPayload=mapper.readValue(payload.value().toString(), UserCreatedPayload.class);
        logger.info(userCreatedPayload.toString());
        simpleMailMessage.setTo(userCreatedPayload.getEmail());
        simpleMailMessage.setSubject("welcome Mail");
        simpleMailMessage.setText("welcome to payments application:");
        javaMailSender.send(simpleMailMessage);

    }

    @KafkaListener(topics = "${wallet.update.topic}", groupId = "walletUpdate")
    public void walletUpdate(ConsumerRecord payload) throws JsonProcessingException {
        logger.info(payload.toString());
        WalletUpdate walletUpdate=mapper.readValue(payload.value().toString(), WalletUpdate.class);
        logger.info(walletUpdate.toString());
        simpleMailMessage.setTo(walletUpdate.getUserEmail());
        simpleMailMessage.setSubject("Wallet updated Mail");
        simpleMailMessage.setText("your current balance is: "+walletUpdate.getBalance());
        javaMailSender.send(simpleMailMessage);
    }

}
