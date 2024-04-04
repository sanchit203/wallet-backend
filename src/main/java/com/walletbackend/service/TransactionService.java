package com.walletbackend.service;

import com.walletbackend.constants.ErrorMessage;
import com.walletbackend.dto.CreateWithdrawRequestDTO;
import com.walletbackend.dto.TransactionDTO;
import com.walletbackend.dto.TransactionDetailResponseDTO;
import com.walletbackend.dto.TransactionResponseDTO;
import com.walletbackend.entity.Transaction;
import com.walletbackend.entity.User;
import com.walletbackend.enums.PaymentStatus;
import com.walletbackend.enums.PaymentType;
import com.walletbackend.exception.InvalidInputDataException;
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

    public List<TransactionResponseDTO> getAllTransactionByUser() {
        User loggedInUser = userService.getLoggedInUser();
        return transactionRepository
                .findAllByUser(loggedInUser)
                .stream()
                .map(transaction -> TransactionResponseDTO
                        .builder()
                        .id(transaction.getId())
                        .type(transaction.getType())
                        .date(transaction.getCreatedAt())
                        .amount(transaction.getAmount())
                        .build()
                )
                .toList();
    }

    public Double getTotalInvestedAmountByUser(User user) {
        List<Transaction> addTransactions = transactionRepository.findAllByUserAndType(user, PaymentType.ADD);

        if (addTransactions == null || addTransactions.isEmpty()) {
            return 0.0;
        }

        return addTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public List<TransactionResponseDTO> getTopNTransactions(User user, int n) {
        return transactionRepository
                .findNMostRecentTransactionsByUserId(user.getId(), n)
                .stream()
                .map(transaction -> TransactionResponseDTO
                        .builder()
                        .id(transaction.getId())
                        .type(transaction.getType())
                        .date(transaction.getCreatedAt())
                        .amount(transaction.getAmount())
                        .build()
                )
                .toList();
    }

    public TransactionDetailResponseDTO getTransactionDetailById(Long id) {
        User loggedInUser = userService.getLoggedInUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, loggedInUser);
        if (transaction == null) {
            throw new InvalidInputDataException(ErrorMessage.TRANSACTION_NOT_FOUND);
        }
        return TransactionDetailResponseDTO
                .builder()
                .type(transaction.getType())
                .status(transaction.getStatus())
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .dateTime(transaction.getCreatedAt())
                .build();
    }

    public void createWithDrawRequest(CreateWithdrawRequestDTO createWithdrawRequestDTO) {
        User loggedInUser = userService.getLoggedInUser();
        if(createWithdrawRequestDTO.getAmount() > loggedInUser.getWithdrawableAmount()) {
            throw new  InvalidInputDataException(ErrorMessage.INVALID_WITHDRAW_AMOUNT);
        }
        Transaction transaction = Transaction
                .builder()
                .type(PaymentType.WITHDRAW)
                .amount(createWithdrawRequestDTO.amount)
                .status(PaymentStatus.PENDING)
                .user(loggedInUser)
                .build();

        transactionRepository.save(transaction);
    }
}
