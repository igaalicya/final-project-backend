package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Vaccines;

public interface VaccineRepo  extends JpaRepository<Vaccines, Integer>  {
	
	public Vaccines findById(String id);
	
	@Query(value = "SELECT * FROM Vaccines WHERE id = :vaccinesId", nativeQuery = true)
    public Iterable<Vaccines> findVaccinesById(int vaccinesId);
	
	@Query(value = "SELECT * FROM Vaccines order by sold desc limit 6 ", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesForHome();
	
	// Vaccines All Category	
	@Query(value = "SELECT count(*) FROM vaccines WHERE vaccine_name like %:vaccineName% AND price >=:minPrice AND price <= :maxPrice",nativeQuery = true)
	public int countVaccines(String vaccineName, int minPrice, int maxPrice);	

	@Query(value = "SELECT * FROM Vaccines WHERE vaccine_name like %:vaccineName% AND price >=:minPrice AND price <= :maxPrice limit 9 offset :offset ", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesPerPage(int offset, String vaccineName, int minPrice, int maxPrice);
	
	@Query(value = "SELECT * FROM Vaccines WHERE vaccine_name like %:vaccineName% AND price >=:minPrice AND price <= :maxPrice order by vaccine_name asc limit 9 offset :offset", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesPerPageNameAsc(int offset, String vaccineName, int minPrice, int maxPrice);
	
	@Query(value = "SELECT * FROM Vaccines WHERE vaccine_name like %:vaccineName% AND price >=:minPrice AND price <= :maxPrice order by vaccine_name desc limit 9 offset :offset", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesPerPageNameDesc(int offset, String vaccineName, int minPrice, int maxPrice);
	
	@Query(value = "SELECT * FROM Vaccines WHERE vaccine_name like %:vaccineName% AND price >=:minPrice AND price <= :maxPrice order by price asc limit 9 offset :offset", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesPerPagePriceAsc(int offset, String vaccineName, int minPrice, int maxPrice);
	
	@Query(value = "SELECT * FROM Vaccines WHERE vaccine_name like %:vaccineName% AND price >=:minPrice AND price <= :maxPrice order by price desc limit 9 offset :offset", nativeQuery = true)
    public Iterable<Vaccines> getVaccinesPerPagePriceDesc(int offset, String vaccineName, int minPrice, int maxPrice);
	
	// Vaccines with category
	@Query(value = "SELECT count(*) FROM vaccines v join categories c on c.id = v.categories_id WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName",nativeQuery = true)
	public int countVaccinesCategories( String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT * from vaccines v join categories c on c.id = v.categories_id WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName limit 9 offset :offset",nativeQuery = true)
	public Iterable<Vaccines> getVaccinesCategories(int offset, String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT * from vaccines v join categories c on c.id = v.categories_id WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName order by vaccine_name asc limit 9 offset :offset",nativeQuery = true)
	public Iterable<Vaccines> getVaccinesCategoriesNameAsc(int offset, String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT * from vaccines v join categories c on c.id = v.categories_id WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName order by vaccine_name desc limit 9 offset :offset",nativeQuery = true)
	public Iterable<Vaccines> getVaccinesCategoriesNameDesc(int offset, String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT * from vaccines v join categories c on c.id = v.categories_id WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName order by price asc limit 9 offset :offset",nativeQuery = true)
	public Iterable<Vaccines> getVaccinesCategoriesPriceAsc(int offset, String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT * from vaccines v join categories c on c.id = v.categories_id WHERE v.price>= :minPrice AND v.price<= :maxPrice AND v.vaccine_name like %:vaccineName% AND c.category_name=:categoriesName order by price desc limit 9 offset :offset",nativeQuery = true)
	public Iterable<Vaccines> getVaccinesCategoriesPriceDesc(int offset, String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	// REPORT
	@Query(value = "SELECT vaccine_name, sold as quantity, vaccines.* FROM vaccines", nativeQuery = true)
    public Iterable<Vaccines> getTransactionReport();

	@Query(value = "SELECT vaccine_name, sold as quantity, vaccines.* FROM vaccines JOIN categories ON categories.id = vaccines.categories_id WHERE vaccine_name like %:vaccineName% AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND category_name=:categoriesName order by sold asc", nativeQuery = true)
	 public Iterable<Vaccines> getTransactionReportCategoriesAsc(String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT vaccine_name, sold as quantity, vaccines.* FROM vaccines JOIN categories ON categories.id = vaccines.categories_id WHERE vaccine_name like %:vaccineName% AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice AND category_name=:categoriesName order by sold desc", nativeQuery = true)
	 public Iterable<Vaccines> getTransactionReportCategoriesDesc(String vaccineName, int minPrice, int maxPrice, String categoriesName);
	
	@Query(value = "SELECT vaccine_name, sold as quantity, vaccines.* FROM vaccines WHERE vaccine_name like %:vaccineName% AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice order by sold asc", nativeQuery = true)
	public Iterable<Vaccines> getTransactionReportAllCategoriesAsc(String vaccineName, int minPrice, int maxPrice);
	
	@Query(value = "SELECT vaccine_name, sold as quantity, vaccines.* FROM vaccines WHERE vaccine_name like %:vaccineName% AND vaccines.price>= :minPrice AND vaccines.price<= :maxPrice order by sold desc", nativeQuery = true)
	public Iterable<Vaccines> getTransactionReportAllCategoriesDesc(String vaccineName, int minPrice, int maxPrice);
	
}
