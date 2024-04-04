package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.constants.Constant;
import com.walletbackend.dto.CreateWithdrawRequestDTO;
import com.walletbackend.dto.TransactionDTO;
import com.walletbackend.dto.TransactionDetailResponseDTO;
import com.walletbackend.dto.TransactionResponseDTO;
import com.walletbackend.entity.Transaction;
import com.walletbackend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDTO), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.GET_ALL_TRANSACTION)
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.getAllTransactionByUser(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionDetailResponseDTO> getTransactionDetailResponseDTO(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.getTransactionDetailById(id), HttpStatus.OK);
    }

    @PostMapping(ApiConstant.CREATE_WITHDRAW_REQUEST)
    public ResponseEntity<String> createWithDrawRequest(@Valid @RequestBody CreateWithdrawRequestDTO createWithdrawRequestDTO) {
        transactionService.createWithDrawRequest(createWithdrawRequestDTO);
        return new ResponseEntity<>(Constant.WITHDRAW_REQUEST_CREATED, HttpStatus.OK);
    }
}
