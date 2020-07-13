package com.cimb.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.finalProject.dao.WishlistRepo;
import com.cimb.finalProject.entity.Wishlists;
import com.cimb.finalProject.service.WishlistService;

@RestController
@RequestMapping("/wishlists")
@CrossOrigin
public class WishlistController {
	@Autowired
	private WishlistRepo wishlistRepo;
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping
	public Iterable<Wishlists> getWishlist() {	
		return wishlistService.getWishlist();
	}
	
	@GetMapping("/{id}")
	public Wishlists getWishlistById(@PathVariable int id) {
		return wishlistRepo.findById(id).get();
	}
	
	@GetMapping("/thisUser")
	public Iterable<Wishlists> getWishlistsByUser(@RequestParam int usersId) {
		return wishlistService.getWishlistsByUser(usersId);
	}
	
	@GetMapping("/check")
	public  Iterable<Wishlists> checkWishlistUsers(@RequestParam int vaccinesId, @RequestParam int usersId) {
		return wishlistService.checkWishlistUsers(vaccinesId, usersId);
	}
		
	@PutMapping
	public Wishlists editWishlist(@RequestBody Wishlists wishlists) {
		return wishlistService.editWishlist(wishlists);
	}
	
	@DeleteMapping("/{id}")
	public void deleteWishlistById(@PathVariable int id) {
		wishlistService.deleteWishlistById(id);
	}
	
	@PostMapping("/{usersId}/{vaccinesId}")
    public Wishlists addToWishlists(@RequestBody Wishlists wishlists, @PathVariable int vaccinesId, @PathVariable int usersId) {
		return wishlistService.addToWishlists(wishlists, vaccinesId, usersId);
	}

}
