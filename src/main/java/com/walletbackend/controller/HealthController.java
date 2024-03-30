package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.HEALTH_PREFIX)
public class HealthController {
    @GetMapping("/")
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("API IS HEALTHY", HttpStatus.OK);
    }
}
