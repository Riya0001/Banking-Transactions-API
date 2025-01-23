package com.riya.bankingtransactionsAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @SequenceGenerator(name = "account_id_sequence", sequenceName = "account_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_sequence")
    private Long id;

    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false)
    private String email;  // Same email can have multiple accounts

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "initialbalance", nullable = false)
    private BigDecimal initialBalance;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Account(String accountHolderName, LocalDate dateOfBirth, String email, String phoneNumber,
                   String accountType, BigDecimal initialBalance, String currency) {
        this.accountHolderName = accountHolderName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Account(@NotEmpty(message = "Account Holder name cannot be empty") String accountHolderName, @NotNull(message = "Date of birth is required") @Past(message = "Date of birth must be in the past") LocalDate dateOfBirth, @NotEmpty(message = "Email cannot be empty") @Email(message = "Invalid email format") String email, @NotEmpty(message = "Phone number cannot be empty") @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number format") String phoneNumber, @NotEmpty(message = "Account type is required") @Pattern(regexp = "^(SAVINGS|CURRENT|BUSINESS|JOINT)$", message = "Invalid account type") String accountType, @NotNull(message = "Balance cannot be empty") @DecimalMin(value = "1", message = "Balance must be greater than 1") BigDecimal initialBalance) {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
