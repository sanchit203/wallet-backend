package com.walletbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @NotNull(message = "Requires userName")
    @JsonProperty("username")
    private String userName;
    @NotNull(message = "Requires userPassword")
    @JsonProperty("password")
    private String userPassword;
}
