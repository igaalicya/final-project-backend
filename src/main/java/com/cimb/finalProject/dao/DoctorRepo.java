package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cimb.finalProject.entity.Doctors;
public interface DoctorRepo extends JpaRepository<Doctors, Integer> {
	@Query(value = "SELECT * FROM Doctors WHERE id = :doctorsId", nativeQuery = true)
    public Iterable<Doctors> findDoctorsById(int doctorsId);
	
	@Query(value = "SELECT * FROM Doctors limit 3", nativeQuery = true)
    public Iterable<Doctors> getDoctorsForHome();
}
