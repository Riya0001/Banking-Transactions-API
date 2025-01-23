package com.riya.bankingtransactionsAPI.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferFundsRequestDTO {

    @NotNull(message = "Source Account Id is required")
    private String senderEmail;

    @NotNull(message = "Destination Account Id is required")
    private String receiverEmail;

    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(value = "1", message = "Amount must be greater than 1")
    private BigDecimal amount;
}
