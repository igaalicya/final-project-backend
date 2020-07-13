package com.cimb.finalProject.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.UserRepo;
import com.cimb.finalProject.dao.VaccineRepo;
import com.cimb.finalProject.dao.WishlistRepo;
import com.cimb.finalProject.entity.Users;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.entity.Wishlists;
import com.cimb.finalProject.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {
	
	@Autowired 
	private WishlistRepo wishlistRepo;
	
	@Autowired
	private VaccineRepo vaccineRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	@Transactional
	public Iterable<Wishlists> getWishlist(){
		return wishlistRepo.findAll();
	}
	
	@Override
	@Transactional
	public Iterable<Wishlists> getWishlistsByUser(int usersId) {
		return wishlistRepo.getWishlistsByUser(usersId);
	}
	
	@Override
	@Transactional
	public Wishlists addWishlist(Wishlists wishlists) {
		wishlists.setId(0);
		return wishlistRepo.save(wishlists);
	}
	
	@Override
	@Transactional
	public Wishlists editWishlist(Wishlists wishlists) {
		Optional<Wishlists> findWishlits = wishlistRepo.findById(wishlists.getId());
		
		if (findWishlits.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + wishlists.getId() + " does not exist");
		
		return wishlistRepo.save(wishlists);
	}
	
	@Override
	@Transactional
	public void deleteWishlistById(int id) {
		Optional<Wishlists> findWishlits = wishlistRepo.findById(id);
		
		if (findWishlits.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + id + " does not exist");
		
		wishlistRepo.deleteById(id);
	}
	
	@Override
	@Transactional
	public Wishlists addToWishlists(Wishlists wishlists, int vaccinesId, int userId) {
	    Vaccines findVaccines = vaccineRepo.findById(vaccinesId).get();

        Users findUsers = userRepo.findById(userId).get();

        if (findVaccines ==  null) 
            throw new RuntimeException("sorry, vaccines not found");

        if (findUsers == null)
            throw new RuntimeException("user not found");

        wishlists.setVaccines(findVaccines);
        wishlists.setUsers(findUsers);
        return wishlistRepo.save(wishlists);
	}
	
	@Override
	@Transactional
	public Iterable<Wishlists> checkWishlistUsers(int vaccinesId, int usersId) {
		return wishlistRepo.checkWishlistUsers(usersId, vaccinesId);
	}
	
}
