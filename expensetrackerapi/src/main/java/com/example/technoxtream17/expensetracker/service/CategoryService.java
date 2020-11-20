package com.example.technoxtream17.expensetracker.service;

import java.util.List;
import java.util.Optional;

import com.example.technoxtream17.expensetracker.model.Category;

public interface CategoryService {
	
	List<Category> getAllCategory();
	
	Optional<Category> getCategory(int id);
	
	Category createCategory(Category category);
	
	Object updateCategory(Category category, int id);
	
	void deleteCategory(int id);
}
