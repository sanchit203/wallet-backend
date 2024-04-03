package com.walletbackend.service;

import com.walletbackend.dto.TransactionDTO;
import com.walletbackend.entity.Transaction;
import com.walletbackend.entity.User;
import com.walletbackend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserService userService;

    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionDTO transactionDTO){
        User loggedInUser = userService.getLoggedInUser();
        Transaction newTransaction = Transaction
                .builder()
                .type(transactionDTO.getType())
                .status(transactionDTO.getStatus())
                .amount(transactionDTO.getAmount())
                .user(loggedInUser)
                .build();

        transactionRepository.save(newTransaction);

        return newTransaction;
    }

    public List<Transaction> getAllTransactionByUser() {
        User loggedInUser = userService.getLoggedInUser();
        return transactionRepository.findAllByUser(loggedInUser);
    }
}
