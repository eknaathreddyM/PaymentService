package com.example.service;

import com.example.Repo.WalletRepo;
import com.example.TransactionInitPayload;
import com.example.TxnCompleted;
import com.example.UserCreatedPayload;
import com.example.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Walletservice {

    @Autowired
    private WalletRepo repo;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${txn.completed.topic}")
    private String txnCompletedTopic;

    public void walletInit(UserCreatedPayload userCreatedPayload){
        Wallet wallet=new Wallet();
        wallet.setUserId(userCreatedPayload.getUserId());
        wallet.setUserEmail(userCreatedPayload.getEmail());
        wallet.setName(userCreatedPayload.getName());
        wallet.setPhone(userCreatedPayload.getPhone());
        wallet.setBalance(100d);
        repo.save(wallet);
    }

    @Transactional
    public void walletTxn(TransactionInitPayload transactionInitPayload){
            Wallet fromWallet=repo.findByUserId(transactionInitPayload.getFromUserId());
            TxnCompleted txnCompleted=new TxnCompleted();
            txnCompleted.setId(transactionInitPayload.getId());
            txnCompleted.setRequestId(transactionInitPayload.getRequestId());
            if(fromWallet.getBalance()<transactionInitPayload.getAmount()){
                txnCompleted.setSuccess(false);
                txnCompleted.setReason("insufficient funds!");

            }else{
                Wallet toWallet=repo.findByUserId(transactionInitPayload.getToUserId());
                fromWallet.setBalance(fromWallet.getBalance()-transactionInitPayload.getAmount());
                toWallet.setBalance(toWallet.getBalance()+ transactionInitPayload.getAmount());
                txnCompleted.setSuccess(true);
                txnCompleted.setReason("transaction completed");
            }

            kafkaTemplate.send(txnCompletedTopic,txnCompleted);
    }
}
