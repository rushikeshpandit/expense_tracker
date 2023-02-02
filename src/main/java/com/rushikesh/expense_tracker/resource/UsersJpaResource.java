package com.rushikesh.expense_tracker.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.repository.UserRepository;

@RestController
@RequestMapping(path = "/api")
public class UsersJpaResource {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<Users> GetUser() {
		List<Users> allUsers = userRepository.findAll();
		return allUsers;
	}
	
	@PostMapping("/users")
	public Users createUser(@RequestBody Users user) {
		
		userRepository.save(user);
		return user;
	}
	
	@DeleteMapping("/users/")
	public ResponseEntity<Void> deleteExpense() {
		userRepository.deleteAll();
		return ResponseEntity.noContent().build();
	}

}
