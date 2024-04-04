package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.constants.Constant;
import com.walletbackend.dto.AdminViewResponseDTO;
import com.walletbackend.dto.AdminViewUserResponseDTO;
import com.walletbackend.dto.TransactionResponseDTO;
import com.walletbackend.dto.UpdateWithdrawlBalanceDTO;
import com.walletbackend.dto.UserBankDetailResponseDTO;
import com.walletbackend.dto.UserBankDetailsRequestDTO;
import com.walletbackend.dto.UserProfileResponseDTO;
import com.walletbackend.entity.Transaction;
import com.walletbackend.entity.User;
import com.walletbackend.enums.PaymentStatus;
import com.walletbackend.enums.PaymentType;
import com.walletbackend.service.TransactionService;
import com.walletbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.USER_DETAILS)
public class UserDetailsController {
    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping(ApiConstant.PROFILE)
    public ResponseEntity<UserProfileResponseDTO> getUserProfile() {
        return new ResponseEntity<>(userService.getUserProfileResponseDTO(), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.BANK_DETAILS)
    public ResponseEntity<UserBankDetailResponseDTO> getUserBankDetail() {
        return new ResponseEntity<>(userService.getUserBankDetailResponseDTO(), HttpStatus.OK);
    }

    @PostMapping(ApiConstant.BANK_DETAILS)
    public ResponseEntity<UserBankDetailResponseDTO> updateUserBankDetails(@Valid @RequestBody UserBankDetailsRequestDTO userBankDetailsRequestDTO) {
        return new ResponseEntity<>(userService.updateUserBankDetails(userBankDetailsRequestDTO), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.GET_ALL_USERS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AdminViewResponseDTO> getAllUsers() {
        List<User> users = userService.findAll();
        List<Transaction> transactions = transactionService
                .findAllTransactionByStatus(PaymentStatus.SUCCESS);

        Map<User, List<Transaction>> userTransactionsMap = transactions
                                                            .stream()
                                                            .collect(Collectors.groupingBy(Transaction::getUser));

        Double totalInvested = transactions.stream()
                .filter(transaction -> transaction.getType() == PaymentType.ADD)
                .mapToDouble(Transaction::getAmount)
                .sum();

        Double totalWithdrawn = transactions.stream()
                .filter(transaction -> transaction.getType() == PaymentType.WITHDRAW)
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<Long, AdminViewUserResponseDTO> userAdminViewUserResponseDTOMap = new HashMap<>();

        users.forEach(user -> {
            List<TransactionResponseDTO> transactionResponseDTOs = Optional.ofNullable(userTransactionsMap.get(user))
                    .map(userTransactions -> userTransactions.stream()
                            .map(transaction -> TransactionResponseDTO.builder()
                                    .id(transaction.getId())
                                    .date(transaction.getCreatedAt())
                                    .amount(transaction.getAmount())
                                    .type(transaction.getType())
                                    .build())
                            .collect(Collectors.toList()))
                    .orElse(List.of());

            AdminViewUserResponseDTO adminViewUserResponseDTO = AdminViewUserResponseDTO.builder()
                    .transactions(transactionResponseDTOs)
                    .email(user.getEmail())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .withdrawable(user.getWithdrawableAmount())
                    .build();

            userAdminViewUserResponseDTOMap.put(user.getId(), adminViewUserResponseDTO);
        });

        return new ResponseEntity<>(AdminViewResponseDTO
                .builder()
                .totalWithdrawn(totalWithdrawn)
                .totalAmountCredited(totalInvested)
                .userResponseDTO(userAdminViewUserResponseDTOMap)
                .build(), HttpStatus.OK);
    }

    @PostMapping(ApiConstant.UPDATE_WITHDRAWL_BALANCE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateWithdrawlBalance(@Valid @RequestBody UpdateWithdrawlBalanceDTO updateWithdrawlBalanceDTO) {
        userService.updateWithDrawlBalance(updateWithdrawlBalanceDTO);
        return new ResponseEntity<>(Constant.WITHDRAWL_BALANCE_UPDATED, HttpStatus.OK);
    }
}
