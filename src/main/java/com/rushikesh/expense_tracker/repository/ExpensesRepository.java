package com.rushikesh.expense_tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rushikesh.expense_tracker.model.Expenses;


public interface ExpensesRepository extends MongoRepository<Expenses, Integer>{
}
