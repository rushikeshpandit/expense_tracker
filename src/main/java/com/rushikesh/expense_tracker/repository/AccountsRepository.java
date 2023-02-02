package com.rushikesh.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.Accounts;


public interface AccountsRepository extends JpaRepository<Accounts, Integer>{
}
