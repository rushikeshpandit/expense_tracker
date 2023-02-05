package com.rushikesh.expense_tracker.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

}
