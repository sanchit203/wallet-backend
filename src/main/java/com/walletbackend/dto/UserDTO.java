package com.walletbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "Requires username")
    @JsonProperty("username")
    private String username;

    @NotNull(message = "Requires name")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Requires email")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Requires phone number")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @NotNull(message = "Requires password")
    @JsonProperty("password")
    private String password;
}
