package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.dto.AuthenticationResponse;
import com.walletbackend.dto.AuthenticationRequest;
import com.walletbackend.dto.UserDTO;
import com.walletbackend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(ApiConstant.SIGN_UP)
    public ResponseEntity<AuthenticationResponse> signUp(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authenticationService.createUser(userDTO), HttpStatus.OK);
    }

    @PostMapping(ApiConstant.SIGN_IN)
    public ResponseEntity<AuthenticationResponse> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.createJwtToken(authenticationRequest), HttpStatus.OK);
    }
}
