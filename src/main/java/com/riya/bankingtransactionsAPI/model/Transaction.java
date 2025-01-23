package com.riya.bankingtransactionsAPI.model;

import com.riya.bankingtransactionsAPI.constants.TransactionType;
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
    private Long accountId;

    @Column(name = "sender_email", nullable = false)
    private String senderEmail;  // Sender's email address

    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;  // Receiver's email address

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "sender_balance", nullable = false)
    private BigDecimal senderBalance;  // Sender's balance after transaction

    @Column(name = "receiver_balance", nullable = false)
    private BigDecimal receiverBalance;  // Receiver's balance after transaction

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "transaction_id", nullable = false, updatable = false)
    private UUID transactionId;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    @Column(name = "status")
    private String status;

    @Column(name = "remarks")
    private String remarks;

    public Transaction(Long accountId, String senderEmail, String receiverEmail, TransactionType transactionType,
                       BigDecimal amount, UUID transactionId, BigDecimal senderBalance, BigDecimal receiverBalance,
                       String status, String remarks) {
        this.accountId = accountId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.amount = amount;
        this.senderBalance = senderBalance;
        this.receiverBalance = receiverBalance;
        this.transactionType = transactionType;
        this.transactionId = (transactionId != null) ? transactionId : UUID.randomUUID();
        this.transactionTime = LocalDateTime.now();
        this.status = (status != null) ? status : "PENDING";
        this.remarks = (remarks != null) ? remarks : "No remarks provided";
    }
}
