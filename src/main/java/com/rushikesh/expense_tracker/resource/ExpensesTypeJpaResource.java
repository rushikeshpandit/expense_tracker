package com.rushikesh.expense_tracker.resource;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.constants.ConstantUtil;
import com.rushikesh.expense_tracker.exception.ExpensesTypeNotFoundException;
import com.rushikesh.expense_tracker.exception.UserNotFoundException;
import com.rushikesh.expense_tracker.model.ExpensesType;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.payload.response.ServiceResponse;
import com.rushikesh.expense_tracker.repository.ExpensesTypeRepository;
import com.rushikesh.expense_tracker.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class ExpensesTypeJpaResource {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ExpensesTypeRepository expensesTypeRepository;

	@GetMapping("/users/{userId}/expensestype")
	public ResponseEntity<?> retrieveExpensesTypes(@PathVariable int userId) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		Collection<ExpensesType> existingExpensesTypes = user.get().getExpensesType();

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpensesTypes);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PostMapping("/users/{userId}/expensestype")
	public ResponseEntity<?> createExpensesType(@PathVariable int userId, @Valid @RequestBody ExpensesType expensesType) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		expensesType.setUser(user.get());
		Collection<ExpensesType> existingExpensesTypes = user.get().getExpensesType();
		existingExpensesTypes.add(expensesType);
		expensesTypeRepository.save(expensesType);

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpensesTypes);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PutMapping("/users/{userId}/expensesType")
	public ResponseEntity<?>  updateExpenseType(@PathVariable int userId, @Valid @RequestBody ExpensesType expensesTypes) {

		ServiceResponse response = new ServiceResponse();

		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		/// Handle Account not found		
		ExpensesType fetchedExpensesTypes = user.get().getExpensesType().stream()
				.filter(expensesType -> expensesType.getId().longValue() == expensesTypes.getId().longValue())
				.findAny()
				.orElse(null);
		if(fetchedExpensesTypes == null)
			throw new ExpensesTypeNotFoundException("id:"+expensesTypes.getId().longValue());

		fetchedExpensesTypes.setExpenseTypeName(expensesTypes.getExpenseTypeName());
		expensesTypeRepository.save(fetchedExpensesTypes);

		Collection<ExpensesType> existingExpensesTypes = user.get().getExpensesType();

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpensesTypes);

		return ResponseEntity
				.ok()
				.body(response);
	}

}
