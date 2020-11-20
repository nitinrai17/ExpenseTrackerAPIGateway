package com.example.technoxtream17.expensetracker.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.technoxtream17.expensetracker.model.Category;
import com.example.technoxtream17.expensetracker.repository.CategoryRepo;
import com.example.technoxtream17.expensetracker.service.CategoryService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
class CategoryServiceImplTest {

	@InjectMocks
	private CategoryService categoryService = new CategoryServiceImpl();
	
	@Mock
	private CategoryRepo categoryRepo;
	
	Category category = new Category(1,"food");
	
	@Test
	void testGetAllCategory() {
		
		given(categoryRepo.findAll()).willReturn(singletonList(category));
		assertEquals(singletonList(category), categoryService.getAllCategory());
		
	}

	@Test
	void testGetCategory() {
		
		given(categoryRepo.findById(1)).willReturn(Optional.of(category));
		assertEquals(Optional.of(category), categoryService.getCategory(1));
	}

	@Test
	void testCreateCategory() {
		given(categoryRepo.save(category)).willReturn(category);
		assertEquals(category, categoryService.createCategory(category));
	}

	@Test
	void testUpdateCategory() {
		given(categoryRepo.existsById(1)).willReturn(true);
		given(categoryRepo.save(category)).willReturn(category);
		assertEquals(category, categoryService.updateCategory(category,1));
	}
	
	@Test 
	void testUpdateCategoryWithError() {
		try {
			categoryService.updateCategory(category, 1);
			fail("GenericNotFoundException not thrown ");
		}catch (Exception e) {
		}
				
	}

	@Test
	void testDeleteCategory() {
		categoryService.deleteCategory(1);
		assertTrue(Boolean.TRUE);
	}

}
