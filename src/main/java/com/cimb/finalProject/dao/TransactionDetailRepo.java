package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.TransactionDetails;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetails, Integer> {
	@Query(value = "SELECT * FROM transaction_details WHERE transactions_id = :transactionsId", nativeQuery = true)
    public Iterable<TransactionDetails> findTransactionDetails(int transactionsId);
	
//	@Query(value = "SELECT vaccine_name, sold  as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
//			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id WHERE transactions.status = 'completed' group by vaccine_name", nativeQuery = true)
//    public Iterable<TransactionDetails> getTransactionReport();
	
	@Query(value = "SELECT vaccine_name, sold as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id group by vaccine_name", nativeQuery = true)
    public Iterable<TransactionDetails> getTransactionReport();
	
//	@Query(value = "SELECT vaccine_name, sold as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
//			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id JOIN categories ON vaccines.categories_id = categories.id"
//			+ "AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND vaccines.vaccine_name like %:vaccineName% AND categories.category_name=:categoriesName group by vaccine_name", nativeQuery = true)
//    public Iterable<TransactionDetails> getTransactionReportCategories(String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	
	// INI TERBARU
	@Query(value = "SELECT vaccine_name, sold as quantity, categories_name, transaction_details.*, vaccines.*, categories.* FROM transaction_details JOIN categories ON vaccines.categories_id = categories.id"
			+ "WHERE vaccines.vaccine_name like %:vaccineName% AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND categories.category_name=:categoriesName group by vaccine_name", nativeQuery = true)
	 public Iterable<TransactionDetails> getTransactionReportCategories(String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
//	@Query(value = "SELECT * FROM vaccines v JOIN Categories c ON v.categories_id = c.id"
//			+ " WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName group by vaccine_name", nativeQuery = true)
//    public Iterable<TransactionDetails> getTransactionReportCategories(String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
//	@Query(value = "SELECT vaccine_name, sum(quantity) as quantity, status, transaction_details.*, vaccines.*, transactions.* FROM transaction_details left JOIN vaccines "
//			+ "ON transaction_details.vaccines_id = vaccines.id JOIN transactions ON transactions.id = transaction_details.transactions_id WHERE transactions.status = 'completed'"
//			+ "AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND vaccines.vaccine_name like %:vaccineName%  group by vaccine_name", nativeQuery = true)
//    public Iterable<TransactionDetails> getTransactionReportAllCategories(String vaccineName, int minPrice, int maxPrice);
//		
	
	//	INI TERBARU
	@Query(value = "SELECT vaccine_name, sold as quantity, transaction_details.*, vaccines.* FROM transaction_details left JOIN vaccines "
			+ "ON transaction_details.vaccines_id = vaccines.id WHERE vaccine_name like %:vaccineName% AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice  group by vaccine_name", nativeQuery = true)
	public Iterable<TransactionDetails> getTransactionReportAllCategories(String vaccineName, int minPrice, int maxPrice);
}
