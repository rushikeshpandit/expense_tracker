package com.rushikesh.expense_tracker.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.exception.UserNotFoundException;
import com.rushikesh.expense_tracker.model.Accounts;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class AccountJpaResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users/{userId}/accounts")
	public List<Accounts> retrieveAccounts(@PathVariable int userId) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		return user.get().getAccounts();
	}
	
	@PostMapping("/users/{userId}/accounts/")
	public List<Accounts> createAccount(@PathVariable int userId, @Valid @RequestBody Accounts account) {
		
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		List<Accounts> existingAccounts = user.get().getAccounts();
		
		user.get().setAccounts(existingAccounts);
		return user.get().getAccounts();
	}

	@PutMapping("/users/{userId}/accounts/")
	public List<Accounts> updateAccounts(@PathVariable int userId, @Valid @RequestBody List<Accounts> accounts) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		user.get().setAccounts(accounts);
		
		return user.get().getAccounts();
	}
	
}
