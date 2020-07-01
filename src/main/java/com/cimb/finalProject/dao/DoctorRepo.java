package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Doctor;

public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

}
