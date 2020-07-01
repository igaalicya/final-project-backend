package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

}
