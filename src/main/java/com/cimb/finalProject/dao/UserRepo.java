package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
