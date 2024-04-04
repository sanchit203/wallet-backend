package com.walletbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminViewResponseDTO {
    public Double totalAmountCredited;
    public Double totalWithdrawn;
    public Map<Long, AdminViewUserResponseDTO> userResponseDTO;
}
