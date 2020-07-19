package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.Carts;

public interface CartService {
	public Iterable<Carts> getCarts();
	
	public Iterable<Carts> getCartsByUser(int usersId);
	
	public Carts addToCart(Carts carts);
	
	public Carts editCarts(Carts carts);
	
	public void deleteCartsById(int id);
	
	public Carts addToCarts(Carts carts, int vaccinesId, int userId);
	
	public Carts updateQuantity(Carts carts, int cartsId, int vaccinesId, int usersId);
	
	public Iterable<Carts> checkCartsUsers(int usersId, int vaccinesId);
	
	public Carts checkQtyCartsUsers(int usersId, int vaccinesId);
}
