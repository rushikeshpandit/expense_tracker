package com.rushikesh.expense_tracker.resource;

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
import com.rushikesh.expense_tracker.exception.AccountNotFoundException;
import com.rushikesh.expense_tracker.exception.ExpensesNotFoundException;
import com.rushikesh.expense_tracker.exception.ExpensesTypeNotFoundException;
import com.rushikesh.expense_tracker.exception.TransactionTypeNotFoundException;
import com.rushikesh.expense_tracker.exception.UserNotFoundException;
import com.rushikesh.expense_tracker.model.Accounts;
import com.rushikesh.expense_tracker.model.Expenses;
import com.rushikesh.expense_tracker.model.ExpensesType;
import com.rushikesh.expense_tracker.model.TransactionType;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.payload.response.ServiceResponse;
import com.rushikesh.expense_tracker.repository.AccountsRepository;
import com.rushikesh.expense_tracker.repository.ExpensesRepository;
import com.rushikesh.expense_tracker.repository.ExpensesTypeRepository;
import com.rushikesh.expense_tracker.repository.TransactionTypeRepository;
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
	@Autowired 
	private TransactionTypeRepository transactionTypeRepository;


	@GetMapping("/users/expenses")
	public ResponseEntity<?> retrieveExpenses(
			@RequestParam(value = "user") int userId, 
			@RequestParam(value = "account") int accountId) {
		ServiceResponse response = new ServiceResponse();

		Optional<Users> user = userRepository.findById(userId);

		if(user.isEmpty())
			throw new UserNotFoundException("id:"+userId);

		/// Handle Account not found		
		Accounts account = user.get().getAccounts().stream()
				.filter(acc -> acc.getId().longValue() == accountId)
				.findAny()
				.orElse(null);
		if(account == null)
			throw new AccountNotFoundException("User with id: " + userId + " and Account with id: "+ accountId + " does not found");

		List<Expenses> existingExpenses = account.getExpenses();
		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(existingExpenses);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PostMapping("/users/expenses")
	public ResponseEntity<?> createExpense(@Valid @RequestBody Users userAccount) {
		ServiceResponse response = new ServiceResponse();
		Users user = userRepository.findById(userAccount.getId().intValue()).orElse(null);
		if(user == null)
			throw new UserNotFoundException("User with id:" + userAccount.getId() + " not found");

		List<Accounts> existingAccounts = user.getAccounts();
		List<Accounts> accountsToBeAdded = userAccount.getAccounts();
		List<ExpensesType> existingExpensesTypes = user.getExpensesType();
		List<Accounts> accountsToBeUpdate = new CopyOnWriteArrayList<Accounts>();
		List<TransactionType> transactionTypes = transactionTypeRepository.findAll();

		existingAccounts.stream()
		.forEach(accounts -> { // existing accounts 
			accountsToBeAdded.stream()
			.forEach(account -> { //  account to be updated
				if(accounts.getId().longValue() == account.getId().longValue()) {
					existingExpensesTypes.stream()
					.forEach(expensesTypes -> { // existing expenses type
						List<Expenses> expensesToBeAdded = account.getExpenses();
						expensesToBeAdded.stream()
						.forEach(expenses -> {
							if(expensesTypes.getId().longValue() == expenses.getExpenseType().getId().longValue()) {
								switch(expenses.getTransactionType().getId()) {
								case 1:
									accounts.setBalance(accounts.getBalance().longValue() + expenses.getAmount().longValue());
									break;
								case 2:
									accounts.setBalance(accounts.getBalance().longValue() - expenses.getAmount().longValue());
									break;
								default:
									break;
								}
								accountsToBeUpdate.add(accounts);
								expenses.setAccount(accounts);
								expenses.setExpenseType(expensesTypes);
								expenses.setUser(user);
								expensesRepository.save(expenses);
							}
						});
					});
				}
			});
		});
		accountsToBeUpdate.stream()
		.forEach(accounts -> {
			accountsRepository.save(accounts);
		});


		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(user);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PutMapping("/users/expenses")
	public ResponseEntity<?> updateExpense(
			@RequestParam(value = "expensesType") int expensesTypeId, 
			@RequestParam(value = "account") int accountId, 
			@RequestParam(value = "transactionType") int transactionTypeId, 
			@RequestParam(value = "expense") int expensesId,
			@Valid @RequestBody Users userAccount) {

		ServiceResponse response = new ServiceResponse();

		Users user = userRepository.findById(userAccount.getId().intValue()).orElse(null);
		if(user == null)
			throw new UserNotFoundException("User with id: " + userAccount.getId() + " not found.");

		Accounts accountToBeReplace = accountsRepository.findById(accountId).orElse(null);
		ExpensesType exensesTypeToBeReplace = expensesTypeRepository.findById(expensesTypeId).orElse(null);
		TransactionType transactionTypeToBeReplace = transactionTypeRepository.findById(transactionTypeId).orElse(null);
		Expenses expensesToBeReplace = expensesRepository.findById(expensesId).orElse(null);

		if(accountToBeReplace == null)
			throw new AccountNotFoundException("Account with id: " + accountId + " not found.");

		if(exensesTypeToBeReplace == null)
			throw new ExpensesTypeNotFoundException("Expenses Type with id: " + expensesTypeId + " not found.");

		if(transactionTypeToBeReplace == null) {
			throw new TransactionTypeNotFoundException("Transaction Type with id: " + transactionTypeId + " not found.");
		}

		if(expensesToBeReplace == null) {
			throw new ExpensesNotFoundException("Transaction with id: " + expensesId + " not found");
		}

		List<Accounts> existingAccounts = user.getAccounts();

		Accounts accountsToBeAdded = userAccount.getAccounts().get(0);
		Expenses expenseToBeAdded = accountsToBeAdded.getExpenses().get(0);
		ExpensesType  expensesTypeToBeAdded = expenseToBeAdded.getExpenseType();
		TransactionType transactionTypeToBeAdded = expenseToBeAdded.getTransactionType();

		List<Accounts> accountsToBeUpdate = new CopyOnWriteArrayList<Accounts>();

		existingAccounts.stream()
		.forEach(existingAccount -> {
			if(existingAccount.getId().longValue() == accountToBeReplace.getId().longValue()) {
				List<Expenses> expenses = existingAccount.getExpenses();
				expenses.stream()
				.forEach(expense -> {
					if(expense.getId().longValue() == expensesToBeReplace.getId().longValue()) {
						List<ExpensesType> existingExpensesTypes = user.getExpensesType();
						existingExpensesTypes.stream()
						.forEach(existingExpensesType -> {
							if(existingExpensesType.getId().longValue() == exensesTypeToBeReplace.getId().longValue()) {

								Accounts finalAccount = accountsRepository.findById(accountsToBeAdded.getId().intValue()).orElse(null);
								ExpensesType  finalExpensesType = expensesTypeRepository.findById(expensesTypeToBeAdded.getId().intValue()).orElse(null); 
								TransactionType finalTransactionType = transactionTypeRepository.findById(transactionTypeToBeAdded.getId().intValue()).orElse(null);

								if(finalAccount == null)
									throw new AccountNotFoundException("Account with id: " + accountsToBeAdded.getId() + " not found.");

								if(finalExpensesType == null)
									throw new ExpensesTypeNotFoundException("Expenses Type with id: " + expensesTypeToBeAdded.getId() + " not found.");

								if(finalTransactionType == null) {
									throw new TransactionTypeNotFoundException("Transaction Type with id: " + transactionTypeId + " not found.");
								}

								switch(finalTransactionType.getName().toUpperCase()) {
								case "INCOME":
									finalAccount.setBalance(finalAccount.getBalance().longValue() + expense.getAmount().longValue());
									break;
								case "EXPENSE":
									finalAccount.setBalance(finalAccount.getBalance().longValue() - expense.getAmount().longValue());
									break;
								default:
									break;

								}

								accountsToBeUpdate.add(finalAccount);

								expense.setAccount(finalAccount);
								expense.setAmount(expenseToBeAdded.getAmount());
								expense.setDescription(expenseToBeAdded.getDescription());
								expense.setExpenseType(finalExpensesType);
								expense.setTransactionType(finalTransactionType);
								expensesRepository.save(expense);
							}
						});
					}
				});
			}
		});

		accountsToBeUpdate.stream()
		.forEach(accounts -> {
			accountsRepository.save(accounts);
		});

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(user);
		return ResponseEntity
				.ok()
				.body(response);
	}

	@PostMapping("/users/transfer")
	public ResponseEntity<?> accountTransfer(
			@RequestParam(value = "account") int accountId,
			@Valid @RequestBody Accounts userAccount) {
		ServiceResponse response = new ServiceResponse();

		Accounts accountToBeReplace = accountsRepository.findById(accountId).orElse(null);
		Accounts finalAccount = accountsRepository.findById(userAccount.getId().intValue()).orElse(null);

		if(accountToBeReplace == null)
			throw new AccountNotFoundException("Account with id: " + accountId + " not found.");
		if(finalAccount == null)
			throw new AccountNotFoundException("Account with id: " + userAccount.getId() + " not found.");

		Expenses expense = userAccount.getExpenses().get(0);

		accountToBeReplace.setBalance(accountToBeReplace.getBalance().longValue() - expense.getAmount().longValue());
		finalAccount.setBalance(finalAccount.getBalance().longValue() + expense.getAmount().longValue());
		expense.setAccount(accountToBeReplace);

		accountsRepository.save(accountToBeReplace);
		accountsRepository.save(finalAccount);
		expensesRepository.save(expense);

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(finalAccount);
		return ResponseEntity
				.ok()
				.body(response);

	}
}
