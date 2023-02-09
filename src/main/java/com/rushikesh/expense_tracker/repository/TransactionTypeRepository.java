package com.rushikesh.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer>{
}

