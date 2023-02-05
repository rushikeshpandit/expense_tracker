package com.rushikesh.expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushikesh.expense_tracker.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, String>{
	Optional<Roles> findByName(String name);
}