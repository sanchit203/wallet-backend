package com.walletbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequestResponseDTO {
    public Long id;
    public String name;
    public String phoneNumber;
    public String bankAccountNumber;
    public String ifscCode;
    public String nameOnBankAccount;
    public Double amount;
}
