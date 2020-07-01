package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.TransactionDetail;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, Integer> {

}
