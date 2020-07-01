package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Wishlist;

public interface WishlistRepo  extends JpaRepository<Wishlist, Integer>  {

}
