package com.cimb.finalProject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	private String categoryName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categories", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Vaccines> vaccines;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Vaccines> getVaccines() {
		return vaccines;
	}

	public void setVaccines(List<Vaccines> vaccines) {
		this.vaccines = vaccines;
	}
	
}
