package com.dilpreet.bankingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreateAccountResponseDTO {
    Long id;
    String email;
    BigDecimal balance;
    String currency;
    String accountHolderName;
}
