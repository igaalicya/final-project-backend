package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.Vaccines;

public interface VaccineService {
	public Iterable<Vaccines> getVaccines();
	
	public Iterable<Vaccines> getVaccinesForHome();
	
	public Iterable<Vaccines> getVaccinesById(int vaccinesId);
	
	public Vaccines addVaccines(Vaccines vaccines);
	
	public Vaccines editVaccines(Vaccines vaccines);
	
	public void deleteVaccineById(int id);
	
	public Vaccines addCategoriesToVaccines(int vaccinesId, int categoriesId);
	
	public Vaccines addVaccines(Vaccines vaccines, int categoriesId);
	
}
