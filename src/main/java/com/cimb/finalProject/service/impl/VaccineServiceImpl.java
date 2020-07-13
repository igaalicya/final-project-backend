package com.cimb.finalProject.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.CategoriesRepo;
import com.cimb.finalProject.dao.VaccineRepo;
import com.cimb.finalProject.entity.Categories;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.service.VaccineService;

@Service
public class VaccineServiceImpl implements VaccineService {
	@Autowired
	private VaccineRepo vaccineRepo;
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Override
	@Transactional 
	public Iterable<Vaccines> getVaccines() {
		return vaccineRepo.findAll();
	}
	
	@Override
	@Transactional
	public Iterable<Vaccines> getVaccinesForHome(){
		return vaccineRepo.getVaccinesForHome();
	}
	
	@Override
	@Transactional
	public Iterable<Vaccines> getVaccinesById(int vaccinesId){
		return vaccineRepo.findVaccinesById(vaccinesId);
	}

	@Override
	@Transactional
	public Vaccines addVaccines(Vaccines vaccines) {
		vaccines.setId(0);
		return vaccineRepo.save(vaccines);
	}
	
	@Override
	@Transactional
	public Vaccines editVaccines(Vaccines vaccines) {
		Optional<Vaccines> findVaccines = vaccineRepo.findById(vaccines.getId());
		
		if (findVaccines.toString() == "Optional.empty")
			throw new RuntimeException("vaccines with id " + vaccines.getId() + " does not exist");
		
		return vaccineRepo.save(vaccines);
	}
	
	@Override
	@Transactional
	public void deleteVaccineById(int id) {
		Vaccines findVaccines = vaccineRepo.findById(id).get();
		
		findVaccines.setCategories(null);
		vaccineRepo.deleteById(id);
		
	}
	
	@Override
	@Transactional
	public Vaccines addCategoriesToVaccines(int vaccinesId, int categoriesId) {
		Vaccines findVaccines = vaccineRepo.findById(vaccinesId).get();
		
		Categories findCategories = categoriesRepo.findById(categoriesId).get();

		findVaccines.setCategories(findCategories);
		
		return vaccineRepo.save(findVaccines);
	}
	
	public Vaccines addVaccines(Vaccines vaccines, int categoriesId) {
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		vaccines.setCategories(findCategories);
		return vaccineRepo.save(vaccines);
	}
	
	
}
