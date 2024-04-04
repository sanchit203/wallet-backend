package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.constants.Constant;
import com.walletbackend.dto.WithdrawPageResponseDTO;
import com.walletbackend.entity.User;
import com.walletbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ApiConstant.WITHDRAW_PAGE)
@RequiredArgsConstructor
public class WithdrawPageController {
    private final UserService userService;

    @GetMapping(Constant.EMPTY_STRING)
    public ResponseEntity<WithdrawPageResponseDTO> getWithdrawPageDetails() {
        User loggedInUser = userService.getLoggedInUser();
        WithdrawPageResponseDTO withdrawPageResponseDTO = WithdrawPageResponseDTO
                .builder()
                .withdrawableAmount(loggedInUser.getWithdrawableAmount())
                .bankAccountNumber(loggedInUser.getBankAccountNumber())
                .nameOnBankAccount(loggedInUser.getNameOnBankAccount())
                .ifscCode(loggedInUser.getIfscCode())
                .build();

        return new ResponseEntity<>(withdrawPageResponseDTO, HttpStatus.OK);
    }
}
