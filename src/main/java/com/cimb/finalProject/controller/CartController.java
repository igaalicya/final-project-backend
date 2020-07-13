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

import com.cimb.finalProject.entity.Carts;
import com.cimb.finalProject.service.CartService;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {
	@Autowired
	private CartService cartService;

	@GetMapping
	public Iterable<Carts> getCarts() {	
		return cartService.getCarts();
	}
	
	@GetMapping("/thisUser")
    public Iterable<Carts> getCartsByUser(@RequestParam int usersId) {
        return cartService.getCartsByUser(usersId);
    }
	
	@GetMapping("/check")
	public  Iterable<Carts> checkCartsUsers(@RequestParam int vaccinesId, @RequestParam int usersId) {
		return cartService.checkCartsUsers(usersId, vaccinesId);
	}
	
	@PostMapping("/{usersId}/{vaccinesId}")
    public Carts addToCart(@RequestBody Carts carts, @PathVariable int vaccinesId, @PathVariable int usersId) {
		return cartService.addToCarts(carts, vaccinesId, usersId);
	}
	

	// kalo data di cart udah ada
	@PutMapping("/updateQuantity/{cartsId}/{usersId}/{vaccinesId}")
	public Carts updateQuantity(@RequestBody Carts carts, @PathVariable int cartsId, @PathVariable int usersId, @PathVariable int vaccinesId) {
		return cartService.updateQuantity(carts, cartsId, vaccinesId, usersId);	
	}
	
//	@PostMapping
//	public Carts addToCart(@RequestBody Carts carts) {
//		return cartService.addToCart(carts);
//	}
	
	@PutMapping
	public Carts editCarts(@RequestBody Carts carts) {
		return cartService.editCarts(carts);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCartsById(@PathVariable int id) {
		cartService.deleteCartsById(id);
	}	
	

}
