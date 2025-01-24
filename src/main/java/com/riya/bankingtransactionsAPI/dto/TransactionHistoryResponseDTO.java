package com.riya.bankingtransactionsAPI.dto;

import com.riya.bankingtransactionsAPI.constants.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class TransactionHistoryResponseDTO {
    private UUID transactionId;
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private BigDecimal updatedBalance;
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
    private String status;
    private String remarks;
}
