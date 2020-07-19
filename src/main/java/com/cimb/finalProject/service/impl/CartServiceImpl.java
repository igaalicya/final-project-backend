package com.cimb.finalProject.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.CartRepo;
import com.cimb.finalProject.dao.UserRepo;
import com.cimb.finalProject.dao.VaccineRepo;
import com.cimb.finalProject.entity.Carts;
import com.cimb.finalProject.entity.Users;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private VaccineRepo vaccineRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	@Transactional 
	public Iterable<Carts> getCarts(){
		return cartRepo.findAll();
	}
	
	@Override
	@Transactional
    public Iterable<Carts> getCartsByUser(int usersId) {
        return cartRepo.findCartByUser(usersId);
    }
	
	@Override
	@Transactional 
	public Carts addToCart(Carts carts) {
		carts.setId(0);
		return cartRepo.save(carts);
	}
	
	@Override
	@Transactional 
	public Carts editCarts(Carts carts) {
		Optional<Carts> findCarts = cartRepo.findById(carts.getId());
		
		if (findCarts.toString() == "Optional.empty")
			throw new RuntimeException("Vaccines with id " + carts.getId() + " does not exist");
		
		return cartRepo.save(carts);
	}
	
	@Override
	@Transactional 
	public void deleteCartsById(int id) {
		Optional<Carts> findCarts = cartRepo.findById(id);
		
		if (findCarts.toString() == "Optional.empty")
			throw new RuntimeException("Vaccines with id " + id + " does not exist");
	
		cartRepo.deleteById(id);
	}
	
	@Override
	@Transactional
	public Carts addToCarts(Carts carts, int vaccinesId, int userId) {
	    Vaccines findVaccines = vaccineRepo.findById(vaccinesId).get();

        Users findUsers = userRepo.findById(userId).get();

        if (findVaccines ==  null) 
            throw new RuntimeException("sorry, vaccines not found");

        if (findUsers == null)
            throw new RuntimeException("user not found");
        

        carts.setVaccines(findVaccines);
        carts.setUsers(findUsers);
        return cartRepo.save(carts);
	}
	
	@Override
	@Transactional
	public Carts updateQuantity(Carts carts, int cartsId, int vaccinesId, int usersId) {
		Users findUsers = userRepo.findById(usersId).get();
		Vaccines findVaccines = vaccineRepo.findById(vaccinesId).get();
		Carts findCarts= cartRepo.findById(cartsId).get();
		
		carts.setId(findCarts.getId());
		carts.setUsers(findUsers);
		carts.setVaccines(findVaccines);
		return cartRepo.save(carts);
	}
	
	@Override
	@Transactional
	public Iterable<Carts> checkCartsUsers(int usersId, int vaccinesId){
		return cartRepo.checkCartsUsers(usersId, vaccinesId);
	}
	
	@Override
	@Transactional
	public Carts checkQtyCartsUsers(int usersId, int vaccinesId){
		return cartRepo.checkQtyCartsUsers(usersId, vaccinesId);
	}
}
