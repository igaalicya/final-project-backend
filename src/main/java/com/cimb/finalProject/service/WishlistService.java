package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.Wishlists;

public interface WishlistService {
	public Iterable<Wishlists> getWishlist();
	
	public Wishlists addWishlist(Wishlists wishlists);
	
	public Wishlists editWishlist(Wishlists wishlists);
	
	public void deleteWishlistById(int id);
	
	public Wishlists addToWishlists(Wishlists wishlists, int vaccinesId, int userId);
	
	public Iterable<Wishlists> getWishlistsByUser(int usersId);
	
	public Iterable<Wishlists> checkWishlistUsers(int vaccinesId, int usersId);
	
}
