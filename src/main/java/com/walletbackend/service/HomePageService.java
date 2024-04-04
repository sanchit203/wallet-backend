package com.walletbackend.service;

import com.walletbackend.dto.HomePageResponseDTO;
import com.walletbackend.dto.TransactionResponseDTO;
import com.walletbackend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {
    private final TransactionService transactionService;
    private final UserService userService;

    public HomePageResponseDTO getHomePageResponse() {
        User loggedInUser = userService.getLoggedInUser();
        Double totalInvestedAmount = transactionService.getTotalInvestedAmountByUser(loggedInUser);
        List<TransactionResponseDTO> transactionResponseDTOList = transactionService.getTopNTransactions(loggedInUser, 3);
        return HomePageResponseDTO
                .builder()
                .totalInvested(totalInvestedAmount)
                .withdrawable(loggedInUser.getWithdrawableAmount())
                .transactions(transactionResponseDTOList)
                .build();
    }
}
