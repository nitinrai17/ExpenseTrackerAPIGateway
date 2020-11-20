package com.example.technoxtream17.expensetracker.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.technoxtream17.expensetracker.model.Category;
import com.example.technoxtream17.expensetracker.model.Expense;
import com.example.technoxtream17.expensetracker.model.User;
import com.example.technoxtream17.expensetracker.repository.ExpenseRepo;
import com.example.technoxtream17.expensetracker.service.ExpenseService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
class ExpenseServiceImplTest {
	
	@InjectMocks
	private ExpenseService expenseService = new ExpenseServiceImpl();
	
	@Mock
	private ExpenseRepo expenseRepo;
	
	Expense expense = new Expense(1,Instant.now(),"Trip to goa", 200.00, new User(1l,"mikey","mikey@gmail.com",null),new Category(1,"Travel"));

	@Test
	void testGetAllExpenses() {
		
		given(expenseRepo.findAll()).willReturn(singletonList(expense));
		assertEquals(singletonList(expense), expenseService.getAllExpenses());
		
	}

	@Test
	void testGetExpense() {
		
		given(expenseRepo.findById(1)).willReturn(Optional.of(expense));
		assertEquals(Optional.of(expense), expenseService.getExpense(1));
	}

	@Test
	void testCreateExpense() {
	
		given(expenseRepo.save(expense)).willReturn(expense);
		assertEquals(expense, expenseService.createExpense(expense));
	}

	@Test
	void testUpdateExpense() {
		given(expenseRepo.existsById(1)).willReturn(true);
		given(expenseRepo.save(expense)).willReturn(expense);
		assertEquals(expense, expenseService.updateExpense(expense,1));
	}
	
	@Test 
	void testUpdateExpenseWithError() {
		try {
			expenseService.updateExpense(expense, 1);
			fail("GenericNotFoundException not thrown ");
		}catch (Exception e) {
		}
				
	}

	@Test
	void testDeleteExpense() {
		expenseService.deleteExpense(1);
		assertTrue(Boolean.TRUE);
	}

}
