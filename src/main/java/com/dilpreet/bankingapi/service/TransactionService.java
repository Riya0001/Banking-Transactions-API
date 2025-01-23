package com.dilpreet.bankingapi.service;

import com.dilpreet.bankingapi.constants.TransactionType;
import com.dilpreet.bankingapi.dto.TransferFundsRequestDTO;
import com.dilpreet.bankingapi.dto.TransferFundsResponseDTO;
import com.dilpreet.bankingapi.exception.AccountNotFoundException;
import com.dilpreet.bankingapi.exception.InsufficientBalanceException;
import com.dilpreet.bankingapi.exception.SameAccountTransferException;
import com.dilpreet.bankingapi.model.Account;
import com.dilpreet.bankingapi.model.Transaction;
import com.dilpreet.bankingapi.repository.AccountRepository;
import com.dilpreet.bankingapi.repository.TransactionRepository;
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
    TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransferFundsResponseDTO transferFunds( TransferFundsRequestDTO request) {
        if (request.getAccountId().equals(request.getCounterAccountId())) {
            throw new SameAccountTransferException("Sender and receiver account id cannot be the same.");
        }
        Account senderAccount = accountRepository.findAccountById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found for account id: " + request.getAccountId()));
        BigDecimal amount = request.getAmount();
        if (senderAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to complete the transaction.");
        }
        Account receiverAccount = accountRepository.findAccountById(request.getCounterAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found for Account id: " + request.getCounterAccountId()));

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        senderAccount.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(senderAccount);

        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
        receiverAccount.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(receiverAccount);
        UUID transactionId = UUID.randomUUID();
        transactionRepository.save(new Transaction(senderAccount.getId(), receiverAccount.getId(), TransactionType.WITHDRAW, amount, transactionId, senderAccount.getBalance()));
        transactionRepository.save(new Transaction(receiverAccount.getId(), senderAccount.getId(), TransactionType.DEPOSIT, amount, transactionId, receiverAccount.getBalance()));
        return TransferFundsResponseDTO.builder()
                .transactionId(transactionId)
                .amount(request.getAmount())
                .accountId(senderAccount.getId())
                .counterAccountId(receiverAccount.getId())
                .updatedBalance(senderAccount.getBalance())
                .build();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionHistory(Long accountId) {
        accountRepository.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for account id: " + accountId));
        return transactionRepository.findByAccountId(accountId, Sort.by(Sort.Direction.DESC, "transactionTime"));
    }
}
