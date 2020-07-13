package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.TransactionDetails;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetails, Integer> {
	@Query(value = "SELECT * FROM transaction_details WHERE transactions_id = :transactionsId", nativeQuery = true)
    public Iterable<TransactionDetails> findTransactionDetails(int transactionsId);
}
