package com.cimb.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.finalProject.entity.TransactionDetails;
import com.cimb.finalProject.service.TransactionDetailService;

@RestController
@RequestMapping("/transactions/details")
@CrossOrigin
public class TransactionDetailController {
	
	@Autowired
	private TransactionDetailService transactionDetailService;
	
	@GetMapping
	public Iterable<TransactionDetails> getTransactionDetail(){
		return transactionDetailService.getTransactionDetail();
	}
	
	@GetMapping("/transactions")
	public Iterable<TransactionDetails> getDetailTransactions(@RequestParam int transactionsId) {
		return transactionDetailService.getDetailTransactions(transactionsId);
	}
	
	@PostMapping("/{vaccinesId}/{transactionsId}")
	public TransactionDetails postTransactionDetails(@RequestBody TransactionDetails transactionDetails, @PathVariable int vaccinesId, @PathVariable int transactionsId) {
		return transactionDetailService.postTransactionDetails(transactionDetails, vaccinesId, transactionsId);
	}
	
}
