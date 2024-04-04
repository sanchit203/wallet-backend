package com.walletbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBankDetailsRequestDTO {
    @NotNull(message = "Requires bank account number")
    private String bankAccountNumber;

    @NotNull(message = "Requires name of account owner")
    private String nameOnBankAccount;

    @NotNull(message = "Requires ifsc code")
    private String ifscCode;
}
