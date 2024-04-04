package com.walletbackend.dto;

import com.walletbackend.enums.PaymentStatus;
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
public class TransactionDetailResponseDTO {
    public Long id;
    public Double amount;
    public PaymentStatus status;
    public PaymentType type;
    public OffsetDateTime dateTime;
}
