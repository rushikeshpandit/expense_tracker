package com.rushikesh.expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.Users;


public interface UserRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
