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
public class AdminViewUserResponseDTO {
    public String name;
    public String phoneNumber;
    public String email;
    public Double withdrawable;
    public List<TransactionResponseDTO> transactions;
}
