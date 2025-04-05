package com.riya.bankingtransactionsAPI.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.riya.bankingtransactionsAPI.dto.CreateAccountRequestDTO;
import com.riya.bankingtransactionsAPI.dto.CreateAccountResponseDTO;
import com.riya.bankingtransactionsAPI.service.AccountService;
import com.riya.bankingtransactionsAPI.exception.AccountAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend requests
@RequestMapping("/api/account") // Base URL for account APIs
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        try {
            CreateAccountResponseDTO response = accountService.createAccount(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // ✅ If balance is less than 1, return a proper error response
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AccountAlreadyExistsException e) {
            // ✅ If email already exists, return a proper error response
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            // ✅ Catch any other unexpected errors
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
