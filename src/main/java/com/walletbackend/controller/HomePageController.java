package com.walletbackend.controller;

import com.walletbackend.constants.ApiConstant;
import com.walletbackend.constants.Constant;
import com.walletbackend.dto.HomePageResponseDTO;
import com.walletbackend.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.HOME_PAGE)
public class HomePageController {
    private final HomePageService homePageService;

    @GetMapping(Constant.EMPTY_STRING)
    public ResponseEntity<HomePageResponseDTO> getHomePageData() {
        return new ResponseEntity<>(homePageService.getHomePageResponse(), HttpStatus.OK);
    }
}
