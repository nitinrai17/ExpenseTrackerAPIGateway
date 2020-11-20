package com.example.technoxtream17.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.technoxtream17.expensetracker.model.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

}
