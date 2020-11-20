package com.example.technoxtream17.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.technoxtream17.expensetracker.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{

	Category findByCategoryName(String name);
}
