package com.example.technoxtream17.expensetracker.controller;

import static com.example.technoxtream17.expensetracker.constant.Paths.API;
import static com.example.technoxtream17.expensetracker.constant.Paths.CATEGORIES;
import static com.example.technoxtream17.expensetracker.constant.Paths.FORWARDSLASH;
import static com.example.technoxtream17.expensetracker.constant.Paths.VERSION;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.technoxtream17.expensetracker.model.Category;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CategoryController categoryController;
	
	Category category = new Category(1,"food");
	final String COMMONURL=FORWARDSLASH+API+FORWARDSLASH+VERSION+FORWARDSLASH;

	@Test
	void testGetCategories() throws Exception {

		List<Category> singletonList = singletonList(category);
		 
		given(categoryController.getCategories()).willReturn(singletonList);
		
		
		mvc.perform(get(COMMONURL+CATEGORIES)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",hasSize(1)))
				.andExpect(jsonPath("$[0].categoryId", is(category.getCategoryId())))
				.andExpect(jsonPath("$[0].categoryName", is(category.getCategoryName())));
	}

	@Test
	void testGetCategory() throws Exception {
		 
		given(categoryController.getCategory(category.getCategoryId())).willReturn(new ResponseEntity(category, HttpStatus.OK));
		
		mvc.perform(get(COMMONURL+CATEGORIES+FORWARDSLASH+1)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("categoryId", is(category.getCategoryId())))
				.andExpect(jsonPath("categoryName", is(category.getCategoryName())));
	}
	
	@Test
	void testGetCategoryWithError() throws Exception {
		mvc.perform(put(COMMONURL+CATEGORIES+FORWARDSLASH)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	void testCreateCategory() throws Exception {
		
		given(categoryController.createCategory(Mockito.any())).willReturn(new ResponseEntity(category, HttpStatus.CREATED));
		
		mvc.perform(post(COMMONURL+FORWARDSLASH+CATEGORIES).content("{ \"categoryName\": \"food\"}")	
			.contentType(APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("categoryId", is(category.getCategoryId())))
			.andExpect(jsonPath("categoryName", is(category.getCategoryName())));
	}
	
	@Test
	void testCreateCategorywithError() throws Exception {
		
		given(categoryController.createCategory(any())).willReturn(new ResponseEntity(category, HttpStatus.CREATED));
		
		mvc.perform(post(COMMONURL+FORWARDSLASH+CATEGORIES).content("{ \"categoryName\": \"\"}")	
			.contentType(APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorCode", is(1001)))
			.andExpect(jsonPath("errorMessage", is("Validation Failed ")));
	}

	@Test
	void testUpdateCategory() throws Exception{
		given(categoryController.updateCategory(any(),eq(category.getCategoryId()))).willReturn(new ResponseEntity(category, HttpStatus.OK));
		
		mvc.perform(put(COMMONURL+FORWARDSLASH+CATEGORIES+FORWARDSLASH+1).content("{ \"categoryId\": 1, \"categoryName\": \"food\"}")	
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("categoryId", is(category.getCategoryId())))
			.andExpect(jsonPath("categoryName", is(category.getCategoryName())));
	}

	@Test
	void testDeleteCategory() throws Exception{
		given(categoryController.deleteCategory(anyInt())).willReturn(new ResponseEntity(HttpStatus.OK));
		
		mvc.perform(delete(COMMONURL+CATEGORIES+FORWARDSLASH+1)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteCategoryWithError() throws Exception{
		given(categoryController.deleteCategory(anyInt())).willReturn(new ResponseEntity(HttpStatus.NOT_FOUND));
		
		mvc.perform(delete(COMMONURL+CATEGORIES+FORWARDSLASH+5)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
