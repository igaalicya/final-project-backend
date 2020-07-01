package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
