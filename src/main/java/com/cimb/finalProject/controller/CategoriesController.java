package com.cimb.finalProject.controller;

import java.util.List;

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

import com.cimb.finalProject.dao.CategoriesRepo;
import com.cimb.finalProject.entity.Categories;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.service.CategoriesService;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController {
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@GetMapping
	public Iterable<Categories> getCategories(){
		return categoriesService.getCategories();
	}
	
	@GetMapping("/id")
    public Categories getById(@RequestParam int id) {
        return categoriesRepo.findById(id).get();
    }
	
	@PostMapping
	public Categories addCategories(@RequestBody Categories categories) {
		return categoriesService.addCategories(categories);
	}
	
	@PutMapping("/edit/{id}")
	public Categories editCategories(@RequestBody Categories categories, @PathVariable int id) {
		return categoriesService.editCategories(categories, id);
	}
	
	@GetMapping("/{categoriesId}/vaccines")
	public List<Vaccines> getVaccinesOfCategories(@PathVariable int categoriesId) {
		return categoriesService.getVaccinesOfCategories(categoriesId);
	}
	
	@DeleteMapping("/{categoriesId}")
	public void deleteCategories(@PathVariable int categoriesId) {
		categoriesService.deleteCategoriesById(categoriesId);
	}

}
