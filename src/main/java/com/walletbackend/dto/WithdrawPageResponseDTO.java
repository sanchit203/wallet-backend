package com.walletbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawPageResponseDTO {
    public Double withdrawableAmount;
    private String bankAccountNumber;
    private String nameOnBankAccount;
    private String ifscCode;
}
