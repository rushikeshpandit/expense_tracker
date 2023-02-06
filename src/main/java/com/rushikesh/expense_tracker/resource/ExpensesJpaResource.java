package com.rushikesh.expense_tracker.resource;

import java.util.List;
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
import com.rushikesh.expense_tracker.exception.ExpensesTypeNotFoundException;
import com.rushikesh.expense_tracker.exception.UserNotFoundException;
import com.rushikesh.expense_tracker.model.Accounts;
import com.rushikesh.expense_tracker.model.Expenses;
import com.rushikesh.expense_tracker.model.ExpensesType;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.payload.response.ServiceResponse;
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


	@GetMapping("/users/{userId}/accounts/{accountId}/expenses")
	public ResponseEntity<?> retrieveExpenses(@PathVariable int userId, @PathVariable int accountId) {
		ServiceResponse response = new ServiceResponse();

		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		Optional<Accounts> fetchedAccount = accountsRepository.findById(accountId);

		if(fetchedAccount.isEmpty())
			throw new AccountNotFoundException("id:"+accountId);

		/// Handle Account not found		
		Accounts account = user.get().getAccounts().stream()
				.filter(accounts -> accounts.getId().longValue() == accountId)
				.findAny()
				.orElse(null);
		if(account == null)
			throw new AccountNotFoundException("id:"+accountId); 

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(account.getExpenses());
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PostMapping("/users/{userId}/accounts/{accountId}/expenses/{expensesTypeId}")
	public ResponseEntity<?> createExpense(@PathVariable int userId, @PathVariable int accountId, @PathVariable int expensesTypeId,
			@Valid @RequestBody Expenses expenses) {
		ServiceResponse response = new ServiceResponse();
		Optional<Users> user = userRepository.findById(userId);

		/// Handle User not found
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		/// Handle Account not found		
		Accounts account = user.get().getAccounts().stream()
				.filter(accounts -> accounts.getId().longValue() == accountId)
				.findAny()
				.orElse(null);
		if(account == null)
			throw new AccountNotFoundException("id:"+accountId); 


		/// Handle Expenses Type not found
		ExpensesType expensesType = user.get().getExpensesType().stream()
				.filter(expensesTypes -> expensesTypes.getId().longValue() == expensesTypeId)
				.findAny()
				.orElse(null);
		if(expensesType == null)
			throw new ExpensesTypeNotFoundException("id:"+expensesTypeId); 

		List<Expenses> existingExpenses = account.getExpenses();

		expenses.setAccount(account);
		expenses.setExpenseType(expensesType);
		expenses.setUser(user.get());

		existingExpenses.add(expenses);
		expensesRepository.save(expenses);

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpenses);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PutMapping("/users/{userId}/accounts/{accountId}/expenses/{expensesTypeId}")
	public ResponseEntity<?> updateExpense(@PathVariable int userId, @PathVariable int accountId, @PathVariable int expensesTypeId,
			@Valid @RequestBody Expenses expense) {

		ServiceResponse response = new ServiceResponse();

		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		/// Handle Account not found
		Accounts account = user.get().getAccounts().stream()
				.filter(accounts -> accounts.getId().longValue() == accountId)
				.findAny()
				.orElse(null);
		if(account == null)
			throw new AccountNotFoundException("id:"+accountId);

		/// Handle Expenses Type not found
		ExpensesType fetchedExpensesTypes = user.get().getExpensesType().stream()
				.filter(expensesType -> expensesType.getId().longValue() == expensesTypeId)
				.findAny()
				.orElse(null);
		if(fetchedExpensesTypes == null)
			throw new ExpensesTypeNotFoundException("id:"+expense.getExpenseType().getId().longValue());
		List<Expenses> existingExpenses = account.getExpenses();

		existingExpenses.remove(expense);

		expense.setAccount(account);
		expense.setExpenseType(fetchedExpensesTypes);
		expense.setUser(user.get());

		existingExpenses.add(expense);
		expensesRepository.save(expense);

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpenses);

		return ResponseEntity
				.ok()
				.body(response);
	}

}
