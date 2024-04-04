package com.walletbackend.repository;

import com.walletbackend.entity.Transaction;
import com.walletbackend.entity.User;
import com.walletbackend.enums.PaymentStatus;
import com.walletbackend.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByUserAndType(User user, PaymentType type);

    @Query(value = "SELECT * FROM transaction t WHERE t.user_id = :userId ORDER BY t.created_at DESC LIMIT :n", nativeQuery = true)
    List<Transaction> findNMostRecentTransactionsByUserId(Long userId, int n);

    Transaction findByIdAndUser(Long id, User user);

    List<Transaction> findAllByStatus(PaymentStatus status);
}
