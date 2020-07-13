package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.Users;

public interface UserService {
//	public Users updateUserAddress(Users user);
	
	public Iterable<Users> findUsersById(int usersId);
}
