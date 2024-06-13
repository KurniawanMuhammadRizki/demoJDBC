package com.kukilabs.demoJDBC.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SwitchActiveWalletDto {
    @NotNull(message = "walletId cannot be null")
    private Long walletId;
}
