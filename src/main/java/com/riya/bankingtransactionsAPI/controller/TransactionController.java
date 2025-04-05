package com.riya.bankingtransactionsAPI.controller;
import com.riya.bankingtransactionsAPI.dto.TransactionHistoryResponseDTO;
import com.riya.bankingtransactionsAPI.dto.TransferFundsRequestDTO;
import com.riya.bankingtransactionsAPI.dto.TransferFundsResponseDTO;
import com.riya.bankingtransactionsAPI.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

import static com.riya.bankingtransactionsAPI.constants.TransactionApi.TRANSACTION_HISTORY_URL;
import static com.riya.bankingtransactionsAPI.constants.TransactionApi.TRANSFER_MONEY_URL;

@CrossOrigin(origins = "http://localhost:3000")
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
                .map(transaction -> TransactionHistoryResponseDTO.builder()
                        .transactionId(transaction.getTransactionId())
                        .senderEmail(transaction.getSenderEmail())
                        .receiverEmail(transaction.getReceiverEmail())
                        .amount(transaction.getAmount())
                        .updatedBalance(transaction.getUpdatedBalance())
                        .transactionType(transaction.getTransactionType())
                        .transactionTime(transaction.getTransactionTime())
                        .status(transaction.getStatus())
                        .remarks(transaction.getRemarks())
                        .build()
                ).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}