package com.rushikesh.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.Users;


public interface UserRepository extends JpaRepository<Users, Integer> {

}
