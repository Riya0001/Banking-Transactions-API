package com.riya.bankingtransactionsAPI.controller;

import com.riya.bankingtransactionsAPI.dto.CreateAccountRequestDTO;
import com.riya.bankingtransactionsAPI.dto.CreateAccountResponseDTO;
import com.riya.bankingtransactionsAPI.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.riya.bankingtransactionsAPI.constants.AccountApi.CREATE_ACCOUNT_URL;


@RestController
@Validated
public class AccountController {

    private final AccountService accountService;

    @Autowired
    AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @PostMapping(CREATE_ACCOUNT_URL)
    public ResponseEntity<CreateAccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        CreateAccountResponseDTO response =  accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}