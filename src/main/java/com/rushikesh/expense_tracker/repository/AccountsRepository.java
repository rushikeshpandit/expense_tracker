package com.rushikesh.expense_tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rushikesh.expense_tracker.model.Accounts;


public interface AccountsRepository extends MongoRepository<Accounts, Integer>{
}
