package com.cimb.finalProject.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.UserRepo;
import com.cimb.finalProject.entity.Users;
import com.cimb.finalProject.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	@Transactional
	public Iterable<Users> findUsersById(int usersId){
		return userRepo.findUsersById(usersId);
	}


}
