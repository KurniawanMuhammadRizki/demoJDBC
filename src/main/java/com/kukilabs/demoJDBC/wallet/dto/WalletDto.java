package com.kukilabs.demoJDBC.wallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WalletDto {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Start amount cannot be null")
    @Min(value = 0, message = "Start amount must be zero or positive")
    private Long startAmount;

    @NotNull(message = "Current amount cannot be null")
    @Min(value = 0, message = "Current amount must be zero or positive")
    private Long currentAmount;

    @NotNull(message = "Currency cannot be null")
    private int currencyId;

    private Long userId;

    private boolean isActive;
}
