package com.riya.bankingtransactionsAPI.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateAccountResponseDTO {
    private Long id;                 // Account ID after creation
    private String accountHolderName; // Account holder's name
    private LocalDate dateOfBirth;    // Date of birth of the account holder
    private String email;             // Email associated with the account
    private String phoneNumber;       // Contact number of the account holder
    private String accountType;       // Type of account created (SAVINGS, CURRENT, etc.)
    private BigDecimal initialBalance;       // Account balance after creation
}
