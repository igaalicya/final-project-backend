package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.TransactionDetails;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetails, Integer> {
	@Query(value = "SELECT * FROM transaction_details WHERE transactions_id = :transactionsId", nativeQuery = true)
    public Iterable<TransactionDetails> findTransactionDetails(int transactionsId);
	
	@Query(value = "SELECT vaccine_name, sum(quantity) as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id WHERE transactions.status = 'completed' group by vaccine_name", nativeQuery = true)
    public Iterable<TransactionDetails> getTransactionReport();
	
	@Query(value = "SELECT vaccine_name, sum(quantity) as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id JOIN vaccines WHERE vaccines.categories_id = categories.id"
			+ " WHERE transactions.status = 'completed'"
			+ "AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND vaccines.vaccine_name like %:vaccineName% AND categories.category_name=:categoriesName group by vaccine_name", nativeQuery = true)
    public Iterable<TransactionDetails> getTransactionReportCategories(String vaccineName, int minPrice, int maxPrice, String categories);
	
	@Query(value = "SELECT vaccine_name, sum(quantity) as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id WHERE transactions.status = 'completed'"
			+ "AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND vaccines.vaccine_name like %:vaccineName%  group by vaccine_name", nativeQuery = true)
    public Iterable<TransactionDetails> getTransactionReportAllCategories(String vaccineName, int minPrice, int maxPrice);
}
