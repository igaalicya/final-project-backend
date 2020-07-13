package com.cimb.finalProject.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cimb.finalProject.dao.DoctorRepo;
import com.cimb.finalProject.dao.TransactionRepo;
import com.cimb.finalProject.dao.UserRepo;
import com.cimb.finalProject.entity.Doctors;
import com.cimb.finalProject.entity.Transactions;
import com.cimb.finalProject.entity.Users;
import com.cimb.finalProject.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired 
	private UserRepo userRepo;
	
	@Override
	@Transactional
	public Iterable<Transactions> getTransactions(){
		return transactionRepo.findAll();
	}
	
	@Override
	@Transactional
	public Transactions getTransactionsById(int transactionsId){
		return transactionRepo.findById(transactionsId).get();
	}
	
	@Override
	@Transactional
	public Transactions postTransactions(Transactions transactions, int doctorsId, int userId) {
		Doctors findDoctors = doctorRepo.findById(doctorsId).get();

        Users findUsers = userRepo.findById(userId).get();

        if (findDoctors ==  null) 
            throw new RuntimeException("sorry, vaccines not found");

        if (findUsers == null)
            throw new RuntimeException("user not found");
        
        transactions.setStatus("pending");
        transactions.setDoctors(findDoctors);
        transactions.setUsers(findUsers);
        return transactionRepo.save(transactions);
	}
	
	@Override
	@Transactional
	public Iterable<Transactions> getTransactionsByStatus(String status) {
		return transactionRepo.getTransactionsByStatus(status);
	}
	
	@Override
	@Transactional
	public Iterable<Transactions> getTransactionsByUser(int usersId) {
		return transactionRepo.getTransactionsByUser(usersId);
	}
	
	@Override
	@Transactional
	public Transactions acceptPayment(@PathVariable int transactionsId, @RequestBody Transactions transactions){

		Transactions findTransactions = transactionRepo.findById(transactionsId).get();
	 
		Date date = new Date();
		String completionDate = date.toString();
		System.out.println(completionDate);

		findTransactions.setCompletionDate(completionDate);
		findTransactions.setStatus("completed");
		
		return transactionRepo.save(findTransactions);
	}
	
}
