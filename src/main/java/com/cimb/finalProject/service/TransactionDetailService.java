package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.TransactionDetails;

public interface TransactionDetailService {
	public Iterable<TransactionDetails> getTransactionDetail();
	
	public TransactionDetails postTransactionDetails(TransactionDetails transactionDetails, int vaccinesId, int transactionId);
		
	public Iterable<TransactionDetails> getDetailTransactions(int transactionsId);
		
}
