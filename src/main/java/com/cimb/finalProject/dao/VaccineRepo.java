package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Vaccine;

public interface VaccineRepo  extends JpaRepository<Vaccine, Integer>  {

}
