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
        // ✅ 1. Check if sender account exists (First priority)
        Account sender = accountRepository.findByEmail(request.getSenderEmail())
                .orElseThrow(() -> new AccountNotFoundException("Sender account does not exist."));

        // ✅ 2. Check if receiver account exists (Second priority)
        Account receiver = accountRepository.findByEmail(request.getReceiverEmail())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account does not exist."));

        // ✅ 3. Prevent sender and receiver from being the same (Third priority)
        if (sender.getEmail().equalsIgnoreCase(receiver.getEmail())) {
            throw new SameAccountTransferException("Sender and receiver email cannot be the same.");
        }

        // ✅ 4. Validate sender's balance (Fourth priority)
        BigDecimal transferAmount = request.getAmount();
        if (sender.getInitialBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to complete the transaction.");
        }

        // ✅ 5. Perform transaction updates atomically
        sender.setInitialBalance(sender.getInitialBalance().subtract(transferAmount));
        receiver.setInitialBalance(receiver.getInitialBalance().add(transferAmount));

        LocalDateTime now = LocalDateTime.now();
        sender.setUpdatedAt(now);
        receiver.setUpdatedAt(now);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // ✅ 6. Generate transaction ID
        UUID transactionId = UUID.randomUUID();

        // ✅ 7. Save transaction records
        saveTransaction(sender, receiver, transferAmount, transactionId, TransactionType.WITHDRAWAL, "Transfer to " + receiver.getEmail());
        saveTransaction(receiver, sender, transferAmount, transactionId, TransactionType.DEPOSIT, "Received from " + sender.getEmail());

        // ✅ 8. Return response
        return TransferFundsResponseDTO.builder()
                .transactionId(transactionId)
                .amount(transferAmount)
                .senderEmail(sender.getEmail())
                .receiverEmail(receiver.getEmail())
                .updatedBalance(sender.getInitialBalance())
                .status("SUCCESS")
                .message("Transfer to " + receiver.getEmail() + " successfully")
                .build();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId, Sort.by(Sort.Direction.DESC, "transactionTime"));
    }

    private void saveTransaction(Account sender, Account receiver, BigDecimal amount, UUID transactionId,
                                 TransactionType type, String description) {
        transactionRepository.save(new Transaction(
                sender.getId(),
                sender.getEmail(),
                receiver.getEmail(),
                type,
                amount,
                transactionId,
                sender.getInitialBalance(),  // Stores updated balance
                receiver.getInitialBalance(),
                "SUCCESS",
                description
        ));
    }
}
