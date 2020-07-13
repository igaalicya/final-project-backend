package com.cimb.finalProject.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.CategoriesRepo;
import com.cimb.finalProject.entity.Categories;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Override
	@Transactional
	public Iterable<Categories> getCategories(){
		return categoriesRepo.findAll();
	}
	
	@Override
	@Transactional
	public Categories addCategories(Categories categories) {
		categories.setId(0);
		return categoriesRepo.save(categories);
	}
	
	@Override
	@Transactional
	public Categories editCategories(Categories categories, int id) {
		Categories findCategories = categoriesRepo.findById(id).get();
		
		categories.setVaccines(findCategories.getVaccines());
		
		return categoriesRepo.save(categories);
	}
	
	@Override
	@Transactional
	public List<Vaccines> getVaccinesOfCategories(int categoriesId){
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		return findCategories.getVaccines();
	}
	
	@Override
	@Transactional
	public void deleteCategoriesById(int id) {
		Optional<Categories> findCategories = categoriesRepo.findById(id);
		
		if (findCategories.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + id + " does not exist");
	
		categoriesRepo.deleteById(id);
	}
}
