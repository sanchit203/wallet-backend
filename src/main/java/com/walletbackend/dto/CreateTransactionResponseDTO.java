package com.walletbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionResponseDTO {
    public List<TransactionResponseDTO> transactions;
    public Double totalInvestedAmount;
    public Double totalWithdrawl;
}
