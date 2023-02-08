package com.rushikesh.expense_tracker.resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

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
import com.rushikesh.expense_tracker.exception.UserNotFoundException;
import com.rushikesh.expense_tracker.model.Accounts;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.payload.response.ServiceResponse;
import com.rushikesh.expense_tracker.repository.AccountsRepository;
import com.rushikesh.expense_tracker.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class AccountJpaResource {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountsRepository accountRepository;

	@GetMapping("/users/{userId}/accounts")
	public ResponseEntity<?> retrieveAccounts(@PathVariable int userId) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("User with id:" + userId + " not cound");

		Collection<Accounts> existingAccounts = user.get().getAccounts();

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingAccounts);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PostMapping("/users/accounts")
	public ResponseEntity<?> createAccount(@Valid @RequestBody Users userAccount) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userAccount.getId().intValue());
		if(user.isEmpty())
			throw new UserNotFoundException("User with id:" + userAccount.getId() + " not cound");

		List<Accounts> existingAccounts = user.get().getAccounts();
		List<Accounts> accountsToBeAdded = userAccount.getAccounts();

		accountsToBeAdded.stream()
		.forEach(account -> {
			account.setUser(user.get());
			existingAccounts.add(account);
			accountRepository.save(account);
		});

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingAccounts);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PutMapping("/users/accounts")
	public ResponseEntity<?> updateAccounts(@Valid @RequestBody Users userAccount) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userAccount.getId().intValue());
		if(user.isEmpty())
			throw new UserNotFoundException("User with id:" + userAccount.getId() + " not cound");


		List<Accounts> existingAccounts = user.get().getAccounts();
		List<Accounts> accountsToBeUpdated = userAccount.getAccounts();
		List<Accounts> finalAccounts = new CopyOnWriteArrayList<Accounts>();

		existingAccounts.stream().forEach(existingAccount -> {
			accountsToBeUpdated.stream().forEach(account -> {
				if(existingAccount.getId().longValue() == account.getId().longValue()) {
					existingAccount.setName(account.getName());
				} 
				finalAccounts.add(existingAccount);
			});
		});
		finalAccounts.stream().forEach(finalAccount -> {
			accountRepository.save(finalAccount);
		});

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingAccounts);

		return ResponseEntity
				.ok()
				.body(response);
	}

}
