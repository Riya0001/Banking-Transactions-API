package com.riya.bankingtransactionsAPI.service;

import com.riya.bankingtransactionsAPI.constants.TransactionType;
import com.riya.bankingtransactionsAPI.dto.TransferFundsRequestDTO;
import com.riya.bankingtransactionsAPI.dto.TransferFundsResponseDTO;
import com.riya.bankingtransactionsAPI.exception.AccountNotFoundException;
import com.riya.bankingtransactionsAPI.exception.InsufficientBalanceException;
import com.riya.bankingtransactionsAPI.exception.SameAccountTransferException;
import com.riya.bankingtransactionsAPI.model.Account;
import com.riya.bankingtransactionsAPI.model.Transaction;
import com.riya.bankingtransactionsAPI.repository.AccountRepository;
import com.riya.bankingtransactionsAPI.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransferFundsResponseDTO transferFunds(TransferFundsRequestDTO request) {
        // Check if sender and receiver emails are the same
        if (request.getSenderEmail().equalsIgnoreCase(request.getReceiverEmail())) {
            throw new SameAccountTransferException("Sender and receiver email cannot be the same.");
        }

        // Retrieve sender and receiver accounts
        Account senderAccount = accountRepository.findByEmail(request.getSenderEmail())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found for email: " + request.getSenderEmail()));

        Account receiverAccount = accountRepository.findByEmail(request.getReceiverEmail())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found for email: " + request.getReceiverEmail()));

        BigDecimal transferAmount = request.getAmount();

        // Check for sufficient balance in sender's account
        if (senderAccount.getInitialBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to complete the transaction.");
        }

        // Perform debit and credit operations
        senderAccount.setInitialBalance(senderAccount.getInitialBalance().subtract(transferAmount));
        senderAccount.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(senderAccount);

        receiverAccount.setInitialBalance(receiverAccount.getInitialBalance().add(transferAmount));
        receiverAccount.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(receiverAccount);

        // Generate a unique transaction ID
        UUID transactionId = UUID.randomUUID();

        // Save sender's transaction record (withdrawal)
        transactionRepository.save(new Transaction(
                senderAccount.getId(),
                senderAccount.getEmail(),
                receiverAccount.getEmail(),
                TransactionType.WITHDRAWAL,
                transferAmount,
                transactionId,
                senderAccount.getInitialBalance(),
                receiverAccount.getInitialBalance(),
                "SUCCESS",
                "Transfer to " + receiverAccount.getEmail()
        ));

        // Save receiver's transaction record (deposit)
        transactionRepository.save(new Transaction(
                receiverAccount.getId(),
                receiverAccount.getEmail(),
                senderAccount.getEmail(),
                TransactionType.DEPOSIT,
                transferAmount,
                transactionId,
                receiverAccount.getInitialBalance(),
                senderAccount.getInitialBalance(),
                "SUCCESS",
                "Received from " + senderAccount.getEmail()
        ));

        // Build and return response DTO
        return TransferFundsResponseDTO.builder()
                .transactionId(transactionId)
                .amount(transferAmount)
                .senderEmail(senderAccount.getEmail())
                .receiverEmail(receiverAccount.getEmail())
                .senderBalance(senderAccount.getInitialBalance())
                .receiverBalance(receiverAccount.getInitialBalance())
                .status("SUCCESS")
                .message("Transfer to " + receiverAccount.getEmail() + " successfully")
                .build();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionHistory(Long id) {
        return transactionRepository.findByAccountId(id);
    }
}
