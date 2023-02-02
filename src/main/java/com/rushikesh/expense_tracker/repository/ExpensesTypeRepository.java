package com.rushikesh.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.ExpensesType;

public interface ExpensesTypeRepository extends JpaRepository<ExpensesType, Integer>{

}
