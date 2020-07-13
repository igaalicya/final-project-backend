package com.cimb.finalProject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vaccines {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	private String vaccineName;
	
	private int price;
	
	private String ageOfDose;
	
	private String description;

	private String brand;
	
	private String image;
	
	private int stock;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "categories_id")
	private Categories categories;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccines", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Carts> carts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccines", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<TransactionDetails> transactionDetails;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "transaction_details_id")
//	private TransactionDetails transactionDetails;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccines", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Wishlists> wishlists;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAgeOfDose() {
		return ageOfDose;
	}

	public void setAgeOfDose(String ageOfDose) {
		this.ageOfDose = ageOfDose;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public List<Carts> getCarts() {
		return carts;
	}

	public void setCarts(List<Carts> carts) {
		this.carts = carts;
	}

	public List<Wishlists> getWishlists() {
		return wishlists;
	}

	public void setWishlists(List<Wishlists> wishlists) {
		this.wishlists = wishlists;
	}

	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

}
