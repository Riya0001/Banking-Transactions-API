package com.dilpreet.bankingapi.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Getter
public class CreateAccountRequestDTO {
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Balance cannot be empty")
    @DecimalMin(value = "1", message = "Balance must be greater than 1")
    private BigDecimal balance;

    @NotEmpty(message = "Currency code is required.")
    @Pattern(regexp = "^(INR|USD|CAD|CNY|EUR|GBP|AUD|JPY|CHF|NZD)$", message = "Invalid currency code.")
    private String currencyCode;

    @NotEmpty(message = "Account Holder name cannot be empty")
    private String accountHolderName;

}
