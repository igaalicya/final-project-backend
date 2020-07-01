package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

}
