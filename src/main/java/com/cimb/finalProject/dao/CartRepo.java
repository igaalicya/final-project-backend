package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Carts;

public interface CartRepo extends JpaRepository<Carts, Integer> {
	@Query(value = "SELECT * FROM Carts WHERE users_id = :usersId", nativeQuery = true)
    public Iterable<Carts> findCartByUser(int usersId);
	
	@Query(value = "SELECT * FROM Carts WHERE users_id = :usersId AND vaccines_id = :vaccinesId", nativeQuery = true)
	public Iterable<Carts> checkCartsUsers(int usersId, int vaccinesId);
	
	@Query(value = "SELECT * FROM Carts WHERE users_id = :usersId AND vaccines_id = :vaccinesId", nativeQuery = true)
	public Carts checkQtyCartsUsers(int usersId, int vaccinesId);
}
