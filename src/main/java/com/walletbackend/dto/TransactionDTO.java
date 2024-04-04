package com.walletbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.walletbackend.enums.PaymentStatus;
import com.walletbackend.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("type")
    private PaymentType type;
    @JsonProperty("id")
    private Long id;
}
