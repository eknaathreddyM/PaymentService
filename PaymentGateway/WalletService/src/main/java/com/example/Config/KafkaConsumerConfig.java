package com.example.Config;

import com.example.Repo.WalletRepo;
import com.example.TransactionInitPayload;
import com.example.UserCreatedPayload;
import com.example.WalletUpdate;
import com.example.entity.Wallet;
import com.example.service.Walletservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerConfig {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Walletservice walletservice;

    @Value("${wallet.update.topic}")
    private String topic;

    @Value("${txn.completed.topic}")
    private String txnCompleted;

    private Logger LOGGER= LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @KafkaListener(topics = "${user.created.topic}", groupId = "wallet")
    public void consume(ConsumerRecord payload) throws JsonProcessingException {
        UserCreatedPayload userCreatedPayload=mapper.readValue(payload.value().toString(), UserCreatedPayload.class);
        walletservice.walletInit(userCreatedPayload);
    }

    @KafkaListener(topics = "${txn.init.topic}", groupId = "txnInit")
    public void txnInit(ConsumerRecord payload) throws JsonProcessingException {
        TransactionInitPayload transactionInitPayload=mapper.readValue(payload.value().toString(), TransactionInitPayload.class);
        walletservice.walletTxn(transactionInitPayload);
    }

}
