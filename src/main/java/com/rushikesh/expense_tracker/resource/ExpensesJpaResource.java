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

import com.rushikesh.expense_tracker.exception.AccountNotFoundException;
import com.rushikesh.expense_tracker.exception.ExpensesNotFoundException;
import com.rushikesh.expense_tracker.exception.ExpensesTypeNotFoundException;
import com.rushikesh.expense_tracker.exception.UserNotFoundException;
import com.rushikesh.expense_tracker.model.Accounts;
import com.rushikesh.expense_tracker.model.Expenses;
import com.rushikesh.expense_tracker.model.ExpensesType;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.repository.AccountsRepository;
import com.rushikesh.expense_tracker.repository.ExpensesRepository;
import com.rushikesh.expense_tracker.repository.ExpensesTypeRepository;
import com.rushikesh.expense_tracker.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class ExpensesJpaResource {
	
	@Autowired
	private ExpensesRepository expensesRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private ExpensesTypeRepository expensesTypeRepository;
	
	@GetMapping("/users/{userId}/accounts")
	public List<Accounts> retrieveAccounts(@PathVariable int userId) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		return user.get().getAccounts();
	}
	
	@GetMapping("/users/{userId}/accounts/{accountId}/expenses")
	public List<Expenses> retrieveExpenses(@PathVariable int userId, @PathVariable int accountId) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		

		Optional<Accounts> fetchedAccount = accountsRepository.findById(accountId);
		
		if(fetchedAccount.isEmpty())
			throw new AccountNotFoundException("id:"+accountId);
		
		Boolean shouldReturn = false;
		
		for(Accounts account: user.get().getAccounts()) {
            if(account.getId().equals(fetchedAccount.get().getId())) {
            	shouldReturn = true;
                break;
            }
        }
		if (shouldReturn) {
			return fetchedAccount.get().getExpenses();
		} else {
			throw new ExpensesNotFoundException("id:"+accountId);
		}
	}

	@GetMapping("/users/{userId}/expensestype")
	public List<ExpensesType> retrieveExpensesTypes(@PathVariable int userId,
			@PathVariable int expensesId) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		return user.get().getExpensesType();
	}
	
	@PostMapping("/users")
	public Users createUser(@RequestBody Users user) {
		userRepository.save(user);
		return user;
	}

	@PostMapping("/users/{userId}/accounts/{accountId}/expenses/{expensesTypeId}")
	public Expenses createExpense(@PathVariable int userId, @PathVariable int accountId, @PathVariable int expensesTypeId,
			 @Valid @RequestBody Expenses expenses) {
		
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		Optional<Accounts> fetchedAccount = accountsRepository.findById(accountId);
		
		if(fetchedAccount.isEmpty())
			throw new AccountNotFoundException("id:"+accountId);
		
		Optional<ExpensesType> fetchedExpensesType = expensesTypeRepository.findById(expensesTypeId);
		
		if(fetchedExpensesType.isEmpty())
			throw new ExpensesTypeNotFoundException("id:"+expensesTypeId);
		
		expenses.setUser(user.get());
		expenses.setAccount(fetchedAccount.get());
		expenses.setExpenseType(fetchedExpensesType.get());
		
		Expenses savedExpenses = expensesRepository.save(expenses);
		return savedExpenses;
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
	
	@PostMapping("/users/{userId}/expensestype")
	public List<ExpensesType> createExpensesType(@PathVariable int userId, @Valid @RequestBody ExpensesType expensesType) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		List<ExpensesType> existingExpensesTypes = user.get().getExpensesType();
		
		user.get().setExpensesType(existingExpensesTypes);
		return user.get().getExpensesType();
		
	}
	
//	@DeleteMapping("/users/{username}/expenses/{id}")
//	public ResponseEntity<Void> deleteExpense(@PathVariable String username,
//			@PathVariable int id) {
//		expensesRepository.deleteById(id);
//		return ResponseEntity.noContent().build();
//	}
//
	@PutMapping("/users/{userId}/accounts/")
	public List<Accounts> updateAccounts(@PathVariable int userId, @Valid @RequestBody List<Accounts> accounts) {
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);
		
		user.get().setAccounts(accounts);
		
		return user.get().getAccounts();
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
