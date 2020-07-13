package com.cimb.finalProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.finalProject.entity.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer> {

}
