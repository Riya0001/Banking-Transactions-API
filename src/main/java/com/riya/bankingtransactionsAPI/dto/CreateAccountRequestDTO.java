package com.riya.bankingtransactionsAPI.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Getter
public class CreateAccountRequestDTO {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Balance cannot be empty")
    @DecimalMin(value = "1", message = "Balance must be greater than 1")
    private BigDecimal initialBalance;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @AssertTrue(message = "User must be at least 18 years old")
    public boolean isAdult() {
        if (dateOfBirth == null) {
            return false;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
    }

    @NotEmpty(message = "Currency code is required.")
    @Pattern(regexp = "^(INR|USD|CAD|CNY|EUR|GBP|AUD|JPY|CHF|NZD)$", message = "Invalid currency code.")
    private String currencyCode;

    @NotEmpty(message = "Account Holder name cannot be empty")
    private String accountHolderName;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotEmpty(message = "Account type is required")
    @Pattern(regexp = "^(SAVINGS|CURRENT|BUSINESS|JOINT)$", message = "Invalid account type")
    private String accountType;

    @NotNull(message = "Balance cannot be empty")
    @DecimalMin(value = "1", message = "Balance must be greater than 1")
    private BigDecimal balance;
}
