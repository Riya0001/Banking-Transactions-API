package com.dilpreet.bankingapi.model;

import com.dilpreet.bankingapi.constants.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_sequence")
    @SequenceGenerator(name = "transaction_id_sequence", sequenceName = "transaction_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "accountId", nullable = false)
    private Long accountId;  // current account id foe which transaction occured

    @Column(name = "counterAccountId", nullable = false)
    private Long counterAccountId; // The account to which amount is deposited

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "updatedBalance", nullable = false)
    private BigDecimal updatedBalance;  // updated balance after transaction

    @Enumerated(EnumType.STRING)
    @Column(name = "transactionType", nullable = false)
    private TransactionType transactionType;

    @Column(name = "transactionId", nullable = false)
    private UUID transactionId;

    @Column(name = "transactionTime", nullable = false)
    private LocalDateTime transactionTime;

    public Transaction(Long accountId, Long counterAccountId, TransactionType transactionType, BigDecimal amount, UUID transactionId, BigDecimal updatedBalance) {
        this.accountId = accountId;
        this.counterAccountId = counterAccountId;
        this.amount = amount;
        this.updatedBalance = updatedBalance;
        this.transactionType = transactionType;
        this.transactionId = transactionId;
        this.transactionTime = LocalDateTime.now();
    }

}