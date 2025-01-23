package com.dilpreet.bankingapi.dto;

import com.dilpreet.bankingapi.constants.TransactionType;
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
    UUID transactionId;
    Long counterAccountId;
    BigDecimal amount;
    BigDecimal updatedBalance;
    TransactionType transactionType;
    LocalDateTime timeStamp;

    public TransactionHistoryResponseDTO(UUID transactionId, Long counterAccountId, BigDecimal amount, BigDecimal updatedBalance, TransactionType transactionType, LocalDateTime timeStamp) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.updatedBalance = updatedBalance;
        this.transactionType = transactionType;
        this.timeStamp = timeStamp;
        this.counterAccountId = counterAccountId;
    }
}
