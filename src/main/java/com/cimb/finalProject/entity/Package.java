package com.cimb.finalProject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Package {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String packageName;
	private String image;
	private int price;
	private int stock;
	private int sold;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="package",cascade = CascadeType.ALL)
//	private List<Vaccines> vaccines;
//	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccines", cascade = CascadeType.ALL)
//	@JsonIgnore
//	private List<Carts> carts;
//	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccines", cascade = CascadeType.ALL)
//	@JsonIgnore
//	private List<TransactionDetails> transactionDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

//	public List<Vaccines> getVaccines() {
//		return vaccines;
//	}
//
//	public void setVaccines(List<Vaccines> vaccines) {
//		this.vaccines = vaccines;
//	}
//
//	public List<Carts> getCarts() {
//		return carts;
//	}
//
//	public void setCarts(List<Carts> carts) {
//		this.carts = carts;
//	}
//
//	public List<TransactionDetails> getTransactionDetails() {
//		return transactionDetails;
//	}
//
//	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
//		this.transactionDetails = transactionDetails;
//	}
	
}
