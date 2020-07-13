package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.Transactions;

public interface TransactionService {
	public Iterable<Transactions> getTransactions();
	
	public Transactions getTransactionsById(int transactionsId);
	
	public Transactions postTransactions(Transactions transactions,  int doctorsId, int userId);
	
	public Iterable<Transactions>  getTransactionsByStatus(String status);
	
	public Transactions acceptPayment(int transactionsId, Transactions transactions);
	
	public Iterable<Transactions> getTransactionsByUser(int usersId);
}
