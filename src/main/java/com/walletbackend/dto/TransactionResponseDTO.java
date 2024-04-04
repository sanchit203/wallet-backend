package com.walletbackend.dto;

import com.walletbackend.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    public Long id;
    public OffsetDateTime date;
    public Double amount;
    public PaymentType type;
}
