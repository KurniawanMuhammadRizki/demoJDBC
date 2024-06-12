package com.kukilabs.demoJDBC.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SwitchActiveWalletDto {
    @NotNull(message = "userId cannot be null")
    private Long userid;
    @NotNull(message = "walletId cannot be null")
    private Long walletId;
}
