package com.rushikesh.expense_tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rushikesh.expense_tracker.model.Users;


public interface UserRepository extends MongoRepository<Users, Integer> {

}
