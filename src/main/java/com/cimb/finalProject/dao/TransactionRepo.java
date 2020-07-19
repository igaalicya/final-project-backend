package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
	public Transactions findById(String id);
	
	@Query(value = "SELECT * FROM Transactions WHERE id = :transactionsId", nativeQuery = true)
    public Iterable<Transactions> findTransactionsById(int transactionsId);
	
	@Query(value = "SELECT * FROM Transactions WHERE status = :status", nativeQuery = true)
	public Iterable<Transactions> getTransactionsByStatus(String status);
	
	@Query(value = "SELECT * FROM Transactions WHERE user_id = :usersId", nativeQuery = true)
	public Iterable<Transactions> getTransactionsByUser(int usersId);
	
	@Query(value = "SELECT count(*) FROM Transactions WHERE status like %:status%",nativeQuery = true)
	public int countTransactions(String status);	
}
