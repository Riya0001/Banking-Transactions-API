package com.dilpreet.bankingapi.service;

import com.dilpreet.bankingapi.dto.CreateAccountRequestDTO;
import com.dilpreet.bankingapi.dto.CreateAccountResponseDTO;
import com.dilpreet.bankingapi.model.Account;
import com.dilpreet.bankingapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public CreateAccountResponseDTO createAccount( CreateAccountRequestDTO request) {
        Account account = new Account(request.getEmail(), request.getBalance(), request.getCurrencyCode(), request.getAccountHolderName());
        Account dbAccount = accountRepository.save(account);
        return CreateAccountResponseDTO.builder()
                .id(dbAccount.getId())
                .email(dbAccount.getEmail())
                .balance(dbAccount.getBalance())
                .currency(dbAccount.getCurrency())
                .accountHolderName(dbAccount.getAccountHolderName())
                .build();
    }

}
