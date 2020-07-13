package com.cimb.finalProject.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.TransactionDetailRepo;
import com.cimb.finalProject.dao.TransactionRepo;
import com.cimb.finalProject.dao.VaccineRepo;
import com.cimb.finalProject.entity.TransactionDetails;
import com.cimb.finalProject.entity.Transactions;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.service.TransactionDetailService;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService{
	
	@Autowired
	private TransactionDetailRepo transactionDetailRepo;
	
	@Autowired
	private VaccineRepo vaccineRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Override
	@Transactional
	public Iterable<TransactionDetails> getTransactionDetail(){
		return transactionDetailRepo.findAll();
	}
	
	@Override
	@Transactional
	public TransactionDetails postTransactionDetails(TransactionDetails transactionDetails, int vaccinesId, int transactionId) {

		 Vaccines findVaccines = vaccineRepo.findById(vaccinesId).get();

	     Transactions findTransactions = transactionRepo.findById(transactionId).get();

	     if (findVaccines ==  null) 
	    	 throw new RuntimeException("sorry, vaccines not found");

	     if (findTransactions == null)
	    	 throw new RuntimeException("sorry, transactions not found");
	        
	     transactionDetails.setVaccines(findVaccines);
	     transactionDetails.setTransactions(findTransactions);
	     return transactionDetailRepo.save(transactionDetails);

	}
	
	public Iterable<TransactionDetails> getDetailTransactions(int transactionsId) {
		return transactionDetailRepo.findTransactionDetails(transactionsId);
	}
}
