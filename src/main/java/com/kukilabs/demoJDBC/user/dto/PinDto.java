package com.kukilabs.demoJDBC.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PinDto {
    @NotNull(message = "Pin is required")
    @Size(min = 6, max = 6, message = "Pin must be exactly six digits")
    private Integer pin;
}
