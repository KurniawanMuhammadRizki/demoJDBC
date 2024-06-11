package com.kukilabs.demoJDBC.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusWalletDto {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private boolean isActive;
}
