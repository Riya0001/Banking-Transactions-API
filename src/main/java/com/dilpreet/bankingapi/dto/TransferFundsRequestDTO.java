package com.dilpreet.bankingapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferFundsRequestDTO {

    @NotNull(message = "Source Account Id is required")
    private Long accountId;

    @NotNull(message = "Destination Account Id is required")
    private Long counterAccountId;

    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(value = "1", message = "Amount must be greater than 1")
    private BigDecimal amount;
}
