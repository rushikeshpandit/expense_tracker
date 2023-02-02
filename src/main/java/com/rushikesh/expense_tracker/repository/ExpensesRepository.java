package com.rushikesh.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.Expenses;


public interface ExpensesRepository extends JpaRepository<Expenses, Integer>{
}
