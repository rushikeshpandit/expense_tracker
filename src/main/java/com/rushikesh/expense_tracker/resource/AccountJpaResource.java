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
import com.rushikesh.expense_tracker.exception.AccountNotFoundException;
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
			throw new UserNotFoundException("id:"+userId);

		Collection<Accounts> existingAccounts = user.get().getAccounts();

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingAccounts);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PostMapping("/users/{userId}/accounts")
	public ResponseEntity<?> createAccount(@PathVariable int userId, @Valid @RequestBody Accounts account) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userId);
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		account.setUser(user.get());

		Collection<Accounts> existingAccounts = user.get().getAccounts(); 
		existingAccounts.add(account);

		accountRepository.save(account);

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingAccounts);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PutMapping("/users/{userId}/accounts")
	public ResponseEntity<?> updateAccounts(@PathVariable int userId, @Valid @RequestBody Accounts accounts) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		/// Handle Account not found		
		Accounts fetchedAccount = user.get().getAccounts().stream()
				.filter(account -> account.getId().longValue() == accounts.getId().longValue())
				.findAny()
				.orElse(null);
		if(fetchedAccount == null)
			throw new AccountNotFoundException("id:"+accounts.getId().longValue());

		fetchedAccount.setName(accounts.getName());
		accountRepository.save(fetchedAccount);

		Collection<Accounts> existingAccounts = user.get().getAccounts();

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingAccounts);

		return ResponseEntity
				.ok()
				.body(response);
	}

}
