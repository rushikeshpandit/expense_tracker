package com.rushikesh.expense_tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rushikesh.expense_tracker.model.ExpensesType;

public interface ExpensesTypeRepository extends MongoRepository<ExpensesType, Integer>{

}
