package com.walletbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBankDetailResponseDTO {
    private String bankAccountNumber;

    private String nameOnBankAccount;

    private String ifscCode;
}
