package com.cimb.finalProject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {
	public Optional<Users> findByUsername(String username);
	
	public Optional<Users> findByEmail(String email);	

	public Optional<Users> findByVerifyToken(String verifyToken);

    @Query(value = "SELECT * FROM Users WHERE username = :username", nativeQuery = true)
    public Iterable<Users> findUsername(String username);
    
    @Query(value = "SELECT * FROM Users WHERE email = :email", nativeQuery = true)
    public Iterable<Users> findEmail(String email);
    
    @Query(value = "SELECT * FROM Users WHERE id = :id", nativeQuery = true)
    public Iterable<Users> findUsersById(int id);

}
