package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Vaccines;

public interface VaccineRepo  extends JpaRepository<Vaccines, Integer>  {
	
	public Vaccines findById(String id);
	
	@Query(value = "SELECT * FROM Vaccines WHERE id = :vaccinesId", nativeQuery = true)
    public Iterable<Vaccines> findVaccinesById(int vaccinesId);
	
	@Query(value = "SELECT * FROM Vaccines limit 6", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesForHome();
	
	@Query(value = "select count(*) from vaccines",nativeQuery = true)
	public int countVaccines();	

	@Query(value = "select count(*) from vaccines v join categories c on c.id = v.categories_id where v.price>= :minPrice and v.price<= :maxPrice and v.vaccine_name like %:vaccineName% and c.category_name=:categoriesName",nativeQuery = true)
	public int countVaccinesCategory(double minPrice, double maxPrice, String vaccineName, String categoriesName);

	@Query(value = "SELECT * FROM Vaccines limit 9 offset :offset", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesPerPage(int offset);
}
