package com.dilpreet.bankingapi.controller;
import com.dilpreet.bankingapi.dto.TransactionHistoryResponseDTO;
import com.dilpreet.bankingapi.dto.TransferFundsRequestDTO;
import com.dilpreet.bankingapi.dto.TransferFundsResponseDTO;
import com.dilpreet.bankingapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.dilpreet.bankingapi.constants.TransactionApi.TRANSACTION_HISTORY_URL;
import static com.dilpreet.bankingapi.constants.TransactionApi.TRANSFER_MONEY_URL;

@RestController
@Validated
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(TRANSFER_MONEY_URL)
    public ResponseEntity<TransferFundsResponseDTO> transferFunds(@Valid @RequestBody TransferFundsRequestDTO request) {
        TransferFundsResponseDTO response = transactionService.transferFunds(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(TRANSACTION_HISTORY_URL)
    public ResponseEntity<List<TransactionHistoryResponseDTO>> getTransactionHistory(@PathVariable Long accountId) {
        List<TransactionHistoryResponseDTO> response = transactionService.getTransactionHistory(accountId)
                .stream()
                .map(transaction -> new TransactionHistoryResponseDTO(
                        transaction.getTransactionId(),
                        transaction.getCounterAccountId(),
                        transaction.getAmount(),
                        transaction.getUpdatedBalance(),
                        transaction.getTransactionType(),
                        transaction.getTransactionTime()
                )).collect(Collectors.toList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}