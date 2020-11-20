package com.example.technoxtream17.expensetracker.service.impl;

import static com.example.technoxtream17.expensetracker.constant.Paths.EXPENSENOTMATCH;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.technoxtream17.expensetracker.error.GenericNotFoundException;
import com.example.technoxtream17.expensetracker.model.Expense;
import com.example.technoxtream17.expensetracker.repository.ExpenseRepo;
import com.example.technoxtream17.expensetracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	MessageSource messageSource;

	@Autowired(required = true)
	private ExpenseRepo expenseRepo;

	public List<Expense> getAllExpenses() {
		return expenseRepo.findAll();
	}

	public Optional<Expense> getExpense(int id) {
		return expenseRepo.findById(id);
	}

	public Expense createExpense(Expense expense) {
		return expenseRepo.save(expense);
	}

	public Object updateExpense(Expense expense, int id) {
		if (expenseRepo.existsById(id) && id == expense.getId()) {
			expenseRepo.save(expense);
		} else {
			throw new GenericNotFoundException(messageSource.getMessage(EXPENSENOTMATCH, null, Locale.ENGLISH));
		}
		return expense;
	}

	public void deleteExpense(int id) {
		expenseRepo.deleteById(id);
	}

}
