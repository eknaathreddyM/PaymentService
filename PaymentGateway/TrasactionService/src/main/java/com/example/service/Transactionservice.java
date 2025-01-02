package com.example.service;

import com.example.TransactionInitPayload;
import com.example.TxnCompleted;
import com.example.dto.TransactionDto;
import com.example.entity.Transaction;
import com.example.enums.TxnStatus;
import com.example.repo.TransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Transactionservice {

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private ObjectMapper mapper;

    @Value("${txn.init.topic}")
    private String initTopic;

    @Value("{txn.completed.topic}")
    private String txnCompleted;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public String initTransaction(TransactionDto transactionDto){
        Transaction transaction=new Transaction();
        transaction.setFromUserId(transactionDto.getFromUserId());
        transaction.setToUserId(transactionDto.getToUserId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setComment(transactionDto.getComment());
        transaction.setTxnId(UUID.randomUUID().toString());
        transaction.setStatus(TxnStatus.PENDING);

        transaction=transactionRepo.save(transaction);
        TransactionInitPayload transactionInitPayload=transactionInitPayload(transaction);
        kafkaTemplate.send(initTopic, transactionInitPayload);

        return transaction.getTxnId();
    }

    @KafkaListener(topics = "${txn.completed.topic}", groupId = "txnCompleted")
    public void transactionCompleted(ConsumerRecord payload) throws JsonProcessingException {
        TxnCompleted txnCompleted=mapper.readValue(payload.value().toString(), TxnCompleted.class);
        Transaction transaction=transactionRepo.findById(txnCompleted.getId()).orElseThrow();
        if(txnCompleted.getSuccess()) transaction.setStatus(TxnStatus.SUCCESS);
        else transaction.setStatus(TxnStatus.FAILED);
        transaction.setReason(txnCompleted.getReason());
        transactionRepo.save(transaction);
    }


    private TransactionInitPayload transactionInitPayload(Transaction transaction){
        TransactionInitPayload payload=new TransactionInitPayload();
        payload.setId(transaction.getId());
        payload.setFromUserId(transaction.getFromUserId());
        payload.setToUserId(transaction.getToUserId());
        payload.setAmount(transaction.getAmount());
//        // payload.setRequestId(transaction.getTxnId()); --mdc request id for log tracing

        return payload;
    }
}
