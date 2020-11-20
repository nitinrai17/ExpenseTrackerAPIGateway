package com.example.technoxtream17.expensetracker.service.impl;

import static com.example.technoxtream17.expensetracker.constant.Paths.CATEGORYNOTMATCH;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.technoxtream17.expensetracker.error.GenericNotFoundException;
import com.example.technoxtream17.expensetracker.model.Category;
import com.example.technoxtream17.expensetracker.repository.CategoryRepo;
import com.example.technoxtream17.expensetracker.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	MessageSource messageSource;

	@Autowired(required = true)
	private CategoryRepo categoryRepo;

	@Override
	public List<Category> getAllCategory() {
		return categoryRepo.findAll();
	}

	public Optional<Category> getCategory(int id) {
		Optional.of(Arrays.asList());
		return categoryRepo.findById(id);
	}

	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}

	public Object updateCategory(Category category, int id) {
		if (categoryRepo.existsById(id) && id == category.getCategoryId()) {
			categoryRepo.save(category);
		} else {
			throw new GenericNotFoundException(messageSource.getMessage(CATEGORYNOTMATCH, null, Locale.ENGLISH));
		}
		return category;
	}

	public void deleteCategory(int id) {
		categoryRepo.deleteById(id);
	}

}
