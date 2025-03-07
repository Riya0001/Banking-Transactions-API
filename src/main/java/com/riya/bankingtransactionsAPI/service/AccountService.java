package com.riya.bankingtransactionsAPI.service;

import com.riya.bankingtransactionsAPI.dto.CreateAccountRequestDTO;
import com.riya.bankingtransactionsAPI.dto.CreateAccountResponseDTO;
import com.riya.bankingtransactionsAPI.exception.AccountAlreadyExistsException;
import com.riya.bankingtransactionsAPI.model.Account;
import com.riya.bankingtransactionsAPI.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        // Check if an account with the same email already exists
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AccountAlreadyExistsException("Account already exists for email: " + request.getEmail());
        }

        // Creating a new Account entity from the request DTO
        Account account = new Account(
                request.getAccountHolderName(),
                request.getDateOfBirth(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getAccountType(),
                request.getInitialBalance()
        );

        // Saving account to database
        Account dbAccount = accountRepository.save(account);

        // Building response DTO from the saved entity
        return CreateAccountResponseDTO.builder()
                .id(dbAccount.getId())
                .accountHolderName(dbAccount.getAccountHolderName())
                .dateOfBirth(dbAccount.getDateOfBirth())
                .email(dbAccount.getEmail())
                .phoneNumber(dbAccount.getPhoneNumber())
                .accountType(dbAccount.getAccountType())
                .balance(dbAccount.getInitialBalance())  // Use the correct field name
                .build();
    }
}
