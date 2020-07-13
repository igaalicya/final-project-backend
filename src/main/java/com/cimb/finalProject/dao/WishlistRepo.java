package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Wishlists;

public interface WishlistRepo  extends JpaRepository<Wishlists, Integer>  {
	@Query(value = "SELECT * FROM Wishlists WHERE user_id = :usersId", nativeQuery = true)
	public Iterable<Wishlists> getWishlistsByUser(int usersId);
	
	@Query(value = "SELECT * FROM Wishlists WHERE user_id = :usersId AND vaccines_id = :vaccinesId", nativeQuery = true)
	public Iterable<Wishlists> checkWishlistUsers(int usersId, int vaccinesId);
}
