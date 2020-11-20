package com.example.technoxtream17.expensetracker.controller;

import static com.example.technoxtream17.expensetracker.constant.Paths.API;
import static com.example.technoxtream17.expensetracker.constant.Paths.EXPENSES;
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

import java.time.Instant;
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
import com.example.technoxtream17.expensetracker.model.Expense;
import com.example.technoxtream17.expensetracker.model.User;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ExpenseController expenseController;
	
	
	final String COMMONURL=FORWARDSLASH+API+FORWARDSLASH+VERSION+FORWARDSLASH;
	Expense expense = new Expense(1,Instant.now(),"Trip to goa", 200.00, new User(1l,"mikey","mikey@gmail.com",null),new Category(1,"Travel"));
	
	@Test
	void testGetExpenses() throws Exception {
		List<Expense> singletonList = singletonList(expense);
		 
		given(expenseController.getExpenses()).willReturn(singletonList);
		
		
		mvc.perform(get(COMMONURL+EXPENSES)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(expense.getId())))
				.andExpect(jsonPath("$[0].description", is(expense.getDescription())))
				.andExpect(jsonPath("$[0].amount", is(expense.getAmount())));
	}

	@Test
	void testGetExpense() throws Exception {
		given(expenseController.getExpense(expense.getId())).willReturn(new ResponseEntity(expense, HttpStatus.OK));
		
		mvc.perform(get(COMMONURL+EXPENSES+FORWARDSLASH+1)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id", is(expense.getId())))
				.andExpect(jsonPath("description", is(expense.getDescription())))
				.andExpect(jsonPath("amount", is(expense.getAmount())));
	}
	
	@Test
	void testGetExpenseWithError() throws Exception {
		mvc.perform(put(COMMONURL+EXPENSES+FORWARDSLASH)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	void testCreateExpense() throws Exception {
		given(expenseController.createExpense(Mockito.any())).willReturn(new ResponseEntity(expense, HttpStatus.CREATED));
		
		mvc.perform(post(COMMONURL+FORWARDSLASH+EXPENSES).content("{ \"id\":200, " + 
				"\"amount\": 900," + 
				"\"category\": {\"categoryId\": 1, \"categoryName\": \"Travel\"}," + 
				"\"description\": \"test\"," + 
				"\"expenseDate\": \"2020-09-01T20:27:31.000Z\""
				+ "}")	
			.contentType(APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id", is(expense.getId())))
			.andExpect(jsonPath("description", is(expense.getDescription())))
			.andExpect(jsonPath("amount", is(expense.getAmount())));
	}

	@Test
	void testCreateExpensewithError() throws Exception {
		given(expenseController.createExpense(any())).willReturn(new ResponseEntity(expense, HttpStatus.CREATED));
		
		mvc.perform(post(COMMONURL+FORWARDSLASH+EXPENSES).content("{ \"id\":200, " + 
				"\"amount\": 900," + 
				"\"category\": {\"categoryId\": 1, \"categoryName\": \"Travel\"}," + 
				"\"description\": \"\"," + 
				"\"expenseDate\": \"2020-09-01T20:27:31.000Z\""
				+ "}")	
			.contentType(APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorCode", is(1001)))
			.andExpect(jsonPath("errorMessage", is("Validation Failed ")));
	}
	
	@Test
	void testUpdateExpense() throws Exception {
		given(expenseController.updateExpense(any(),eq(expense.getId()))).willReturn(new ResponseEntity(expense, HttpStatus.OK));
		
		mvc.perform(put(COMMONURL+FORWARDSLASH+EXPENSES+FORWARDSLASH+1).content("{ \"id\":200, " + 
				"\"amount\": 900," + 
				"\"category\": {\"categoryId\": 1, \"categoryName\": \"Travel\"}," + 
				"\"description\": \"test\"," + 
				"\"expenseDate\": \"2020-09-01T20:27:31.000Z\""
				+ "}")	
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", is(expense.getId())))
			.andExpect(jsonPath("description", is(expense.getDescription())))
			.andExpect(jsonPath("amount", is(expense.getAmount())));
	}

	@Test
	void testDeleteExpense() throws Exception {
		given(expenseController.deleteExpense(anyInt())).willReturn(new ResponseEntity(HttpStatus.OK));
		
		mvc.perform(delete(COMMONURL+EXPENSES+FORWARDSLASH+1)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteExpenseWithError() throws Exception{
		given(expenseController.deleteExpense(anyInt())).willReturn(new ResponseEntity(HttpStatus.NOT_FOUND));
		
		mvc.perform(delete(COMMONURL+EXPENSES+FORWARDSLASH+5)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
