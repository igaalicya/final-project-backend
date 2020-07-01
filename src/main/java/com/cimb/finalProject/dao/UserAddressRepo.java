package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.UserAddress;

public interface UserAddressRepo extends JpaRepository<UserAddress, Integer> {

}
