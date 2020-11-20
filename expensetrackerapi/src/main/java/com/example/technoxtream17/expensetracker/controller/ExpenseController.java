package com.example.technoxtream17.expensetracker.controller;

import static com.example.technoxtream17.expensetracker.constant.Paths.API;
import static com.example.technoxtream17.expensetracker.constant.Paths.BEGINBRACES;
import static com.example.technoxtream17.expensetracker.constant.Paths.CATEGORY;
import static com.example.technoxtream17.expensetracker.constant.Paths.ENDBRACES;
import static com.example.technoxtream17.expensetracker.constant.Paths.EXPENSE;
import static com.example.technoxtream17.expensetracker.constant.Paths.EXPENSEIDNOTFOUND;
import static com.example.technoxtream17.expensetracker.constant.Paths.EXPENSENOTFOUND;
import static com.example.technoxtream17.expensetracker.constant.Paths.EXPENSES;
import static com.example.technoxtream17.expensetracker.constant.Paths.FORWARDSLASH;
import static com.example.technoxtream17.expensetracker.constant.Paths.ID;
import static com.example.technoxtream17.expensetracker.constant.Paths.VERSION;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.technoxtream17.expensetracker.error.GenericNotFoundException;
import com.example.technoxtream17.expensetracker.model.Expense;
import com.example.technoxtream17.expensetracker.payload.ApiResponse;
import com.example.technoxtream17.expensetracker.service.ExpenseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(FORWARDSLASH + API + FORWARDSLASH + VERSION)
@Validated
@Api
public class ExpenseController {

	private static final Logger LOGGER = getLogger(ExpenseController.class);
	
	@Autowired
	MessageSource messageSource;

	@Autowired(required = true)
	private ExpenseService expenseService;

	@ApiOperation("Get all Expenses")
	@GetMapping(FORWARDSLASH + EXPENSES)
	Collection<Expense> getExpenses() {
		LOGGER.info("ExpenseController.getExpenses()");
		return expenseService.getAllExpenses();
	}

	@ApiOperation("Get Expense by Id")
	@GetMapping(FORWARDSLASH + EXPENSES + FORWARDSLASH + BEGINBRACES + ID + ENDBRACES)
	ResponseEntity<?> getExpense(@PathVariable(ID) int id) {
		LOGGER.info("ExpenseController.getExpense({})",id);
		Optional<Expense> expense = expenseService.getExpense(id);
		if (!expense.isPresent()) {
			throw new GenericNotFoundException(messageSource.getMessage(EXPENSENOTFOUND, null, Locale.ENGLISH));
		}
		return ResponseEntity.ok().body(expense.get());
	}

	@ApiOperation("Create Expense ")
	@PostMapping(FORWARDSLASH + EXPENSES)
	ResponseEntity<ApiResponse> createExpense(@Valid @RequestBody Expense expense) throws URISyntaxException {
		LOGGER.info("ExpenseController.createExpense({}) ",expense);
		Expense result = expenseService.createExpense(expense);
		return ResponseEntity.created(new URI(
				FORWARDSLASH + API + FORWARDSLASH + VERSION + FORWARDSLASH + CATEGORY + FORWARDSLASH + result.getId()))
				.body(new ApiResponse(result, true));
	}

	@ApiOperation("Update Expense ")
	@PutMapping(FORWARDSLASH + EXPENSES + FORWARDSLASH + BEGINBRACES + ID + ENDBRACES)
	ResponseEntity<Object> updateExpense(@Valid @RequestBody Expense expense, @PathVariable(ID) int id)
			throws URISyntaxException {
		LOGGER.info("ExpenseController.updateExpense({})",id);
		Object result = expenseService.updateExpense(expense, id);
		return ResponseEntity.ok().body(new ApiResponse(result, true));
	}

	@ApiOperation("Delete Expense ")
	@DeleteMapping(FORWARDSLASH + EXPENSES + FORWARDSLASH + BEGINBRACES + ID + ENDBRACES)
	ResponseEntity<?> deleteExpense(@PathVariable(ID) int id) {
		LOGGER.info("ExpenseController.deleteExpense({})",id);
		Optional<Expense> expense = expenseService.getExpense(id);
		if (!expense.isPresent()) {
			throw new GenericNotFoundException(messageSource.getMessage(EXPENSEIDNOTFOUND, null, Locale.ENGLISH));
		}
		expenseService.deleteExpense(id);
		return ResponseEntity.ok().build();
	}

}
