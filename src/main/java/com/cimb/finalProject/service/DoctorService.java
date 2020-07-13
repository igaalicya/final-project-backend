package com.cimb.finalProject.service;

import com.cimb.finalProject.entity.Doctors;

public interface DoctorService {
	public Iterable<Doctors> getDoctors();
	
	public Iterable<Doctors> getDoctorsForHome();
	
	public Iterable<Doctors> getDoctorsById(int doctorsId);
	
	public Doctors addDoctors(Doctors doctors);
	
	public void deleteDoctorById(int id);
	
	public Doctors editDoctors(Doctors doctors);
}
