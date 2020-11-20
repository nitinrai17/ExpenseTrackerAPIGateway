package com.example.technoxtream17.expensetracker.service;

import java.util.List;
import java.util.Optional;

import com.example.technoxtream17.expensetracker.model.Expense;

public interface ExpenseService {
	
	List<Expense> getAllExpenses();
	
	Optional<Expense> getExpense(int id);
	
	Expense createExpense(Expense expense);
	
	Object updateExpense(Expense expense, int id);
	
	void deleteExpense(int id);

}
