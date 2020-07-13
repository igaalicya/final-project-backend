package com.cimb.finalProject.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.finalProject.dao.DoctorRepo;
import com.cimb.finalProject.entity.Doctors;
import com.cimb.finalProject.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Override
	@Transactional 
	public Iterable<Doctors> getDoctors() {
		return doctorRepo.findAll();
	}
	
	@Override
	@Transactional 
	public Iterable<Doctors> getDoctorsForHome() {
		return doctorRepo.getDoctorsForHome();
	}
	
	@Override
	@Transactional
	public Iterable<Doctors> getDoctorsById(int doctorsId){
		return doctorRepo.findDoctorsById(doctorsId);
	}

	@Override
	@Transactional
	public Doctors addDoctors(Doctors doctors) {
		doctors.setId(0);
		return doctorRepo.save(doctors);
	}
	
	@Override
	@Transactional
	public Doctors editDoctors(Doctors doctors) {
		Optional<Doctors> findDoctors = doctorRepo.findById(doctors.getId());
		
		if (findDoctors.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + doctors.getId() + " does not exist");
	
		return doctorRepo.save(doctors);
	}
	
	@Override
	@Transactional
	public void deleteDoctorById(int id) {
		Optional<Doctors> findDoctors = doctorRepo.findById(id);
		
		if (findDoctors.toString() == "Optional.empty")
			throw new RuntimeException("Product with id " + id + " does not exist");
		
		doctorRepo.deleteById(id);
		
	}

}
