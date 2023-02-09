package com.rushikesh.expense_tracker.resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.constants.ConstantUtil;
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

	@GetMapping("/users/expensestype")
	public ResponseEntity<?> retrieveExpensesTypes(@RequestParam(value = "user") int userId) {
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

	@PostMapping("/users/expensestype")
	public ResponseEntity<?> createExpensesType(@Valid @RequestBody Users userAccount)  {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userAccount.getId().intValue());
		if(user.isEmpty())
			throw new UserNotFoundException("User with id:" + userAccount.getId() + " not found");

		List<ExpensesType> existingExpensesTypes = user.get().getExpensesType();
		List<ExpensesType> expensesTypesToBeAdded = userAccount.getExpensesType();

		expensesTypesToBeAdded.stream()
		.forEach(expensesType -> {
			expensesType.setUser(user.get());
			existingExpensesTypes.add(expensesType);
			expensesTypeRepository.save(expensesType);
		});

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpensesTypes);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PutMapping("/users/expensestype")
	public ResponseEntity<?>  updateExpenseType(@Valid @RequestBody Users userAccount) {

		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userAccount.getId().intValue());
		if(user.isEmpty())
			throw new UserNotFoundException("User with id:" + userAccount.getId() + " not found");

		List<ExpensesType> existingExpensesTypes = user.get().getExpensesType();
		List<ExpensesType> expensesTypesToBeUpdated = userAccount.getExpensesType();
		List<ExpensesType> finalExpensesTypes = new CopyOnWriteArrayList<ExpensesType>();

		existingExpensesTypes.stream().forEach(existingExpensesType -> {
			expensesTypesToBeUpdated.stream().forEach(expensType -> {
				if(existingExpensesType.getId().longValue() == expensType.getId().longValue()) {
					existingExpensesType.setExpenseTypeName(expensType.getExpenseTypeName());
				} 
				finalExpensesTypes.add(existingExpensesType);
			});
		});
		finalExpensesTypes.stream().forEach(finalAccount -> {
			expensesTypeRepository.save(finalAccount);
		});

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpensesTypes);

		return ResponseEntity
				.ok()
				.body(response);
	}

}
