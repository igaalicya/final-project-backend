package com.cimb.finalProject.service;

import java.util.List;

import com.cimb.finalProject.entity.Categories;
import com.cimb.finalProject.entity.Vaccines;

public interface CategoriesService {
	public Iterable<Categories> getCategories();
	
	public Categories addCategories(Categories categories);
	
	public Categories editCategories(Categories categories, int id);
	
	public List<Vaccines> getVaccinesOfCategories(int categoriesId);
	
	public void deleteCategoriesById(int id);
}
