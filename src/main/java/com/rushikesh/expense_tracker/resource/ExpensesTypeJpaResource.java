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
import com.rushikesh.expense_tracker.model.ExpensesType;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class ExpensesTypeJpaResource {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users/{userId}/expensestype")
	public List<ExpensesType> retrieveExpensesTypes(@PathVariable int userId,
			@PathVariable int expensesId) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		return user.get().getExpensesType();
	}
	
	@PostMapping("/users/{userId}/expensestype")
	public List<ExpensesType> createExpensesType(@PathVariable int userId, @Valid @RequestBody ExpensesType expensesType) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		List<ExpensesType> existingExpensesTypes = user.get().getExpensesType();
		
		user.get().setExpensesType(existingExpensesTypes);
		return user.get().getExpensesType();
		
	}
	
	@PutMapping("/users/{userId}/expensesType/")
	public List<ExpensesType> updateExpenseType(@PathVariable int userId, @Valid @RequestBody List<ExpensesType> expensesTypes) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		user.get().setExpensesType(expensesTypes);
		
		return user.get().getExpensesType();
	}
}
