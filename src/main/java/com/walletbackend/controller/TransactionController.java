package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.dto.TransactionDTO;
import com.walletbackend.entity.Transaction;
import com.walletbackend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstant.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(ApiConstant.CREATE_TRANSACTION)
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDTO), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.GET_ALL_TRANSACTION)
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.getAllTransactionByUser(), HttpStatus.OK);
    }
}
