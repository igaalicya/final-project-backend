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

import com.cimb.finalProject.dao.TransactionDetailRepo;
import com.cimb.finalProject.entity.TransactionDetails;
import com.cimb.finalProject.service.TransactionDetailService;

@RestController
@RequestMapping("/transactions/details")
@CrossOrigin
public class TransactionDetailController {
	
	@Autowired
	private TransactionDetailRepo transactionDetailRepo;
	
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
	
	@GetMapping("/report")
	public Iterable<TransactionDetails> getTransactionReport(){
		return transactionDetailRepo.getTransactionReport();
	}
	
	@GetMapping("/report/new")
	public Iterable<TransactionDetails> getTransactionReportNew(){
		return transactionDetailRepo.getTransactionReport();
	}
	
	@GetMapping("/report/categories/{minPrice}/{maxPrice}")
	public Iterable<TransactionDetails> getTransactionReport(@RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice, @RequestParam String categoriesName){
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return transactionDetailRepo.getTransactionReportCategories(vaccineName, minPrice, maxPrice, categoriesName);
	}
	
	@GetMapping("/report/all/{minPrice}/{maxPrice}")
	public Iterable<TransactionDetails> getAllTransactionReport(@RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice){
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return transactionDetailRepo.getTransactionReportAllCategories(vaccineName, minPrice, maxPrice);
	}
	
}
