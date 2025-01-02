package com.example.controller;

import com.example.dto.TransactionDto;
import com.example.service.Transactionservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction-service")
public class TransactionController {

    @Autowired
    private Transactionservice transactionservice;

    @PostMapping("/txn")
    public ResponseEntity<String> initTransaction(@RequestBody @Valid TransactionDto transactionDto){
        String txnId= transactionservice.initTransaction(transactionDto);
        return ResponseEntity.accepted().body(txnId);
    }

}
