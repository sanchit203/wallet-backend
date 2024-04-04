package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.dto.UserBankDetailResponseDTO;
import com.walletbackend.dto.UserBankDetailsRequestDTO;
import com.walletbackend.dto.UserProfileResponseDTO;
import com.walletbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.USER_DETAILS)
public class UserDetailsController {
    private final UserService userService;

    @GetMapping(ApiConstant.PROFILE)
    public ResponseEntity<UserProfileResponseDTO> getUserProfile() {
        return new ResponseEntity<>(userService.getUserProfileResponseDTO(), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.BANK_DETAILS)
    public ResponseEntity<UserBankDetailResponseDTO> getUserBankDetail () {
        return new ResponseEntity<>(userService.getUserBankDetailResponseDTO(), HttpStatus.OK);
    }

    @PostMapping(ApiConstant.BANK_DETAILS)
    public ResponseEntity<UserBankDetailResponseDTO> updateUserBankDetails(@Valid @RequestBody UserBankDetailsRequestDTO userBankDetailsRequestDTO) {
        return new ResponseEntity<>(userService.updateUserBankDetails(userBankDetailsRequestDTO), HttpStatus.OK);
    }
}
