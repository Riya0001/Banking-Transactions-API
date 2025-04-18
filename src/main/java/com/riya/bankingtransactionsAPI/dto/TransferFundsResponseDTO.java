package com.riya.bankingtransactionsAPI.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
public class TransferFundsResponseDTO {
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private UUID transactionId;
    private BigDecimal updatedBalance;
    private String status;
    private String message;
}
