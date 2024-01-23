package com.example.onlinestore.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinestore.dao.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>  {
	
}