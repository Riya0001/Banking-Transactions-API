package com.riya.bankingtransactionsAPI.repository;

import com.riya.bankingtransactionsAPI.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Find an account by its email (to support transactions based on email)
    Optional<Account> findByEmail(String email);
}
