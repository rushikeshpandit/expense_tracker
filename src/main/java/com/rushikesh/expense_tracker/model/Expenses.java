package com.rushikesh.expense_tracker.model;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@DynamicUpdate
@Table(name = "expenses")
public class Expenses extends Audit {

	public Expenses() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long expensesId;

	@Column(name = "expense_description", nullable = false)
	private String description;

	@PositiveOrZero
	@Column(name = "expense_amount", nullable = false)
	private Long amount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private Users user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "expense_type_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonIgnore
	private ExpensesType expenseType;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "accounts_id", referencedColumnName = "accountId")
	@JsonIgnoreProperties("expenses")
	@JsonIgnore
	private Accounts account;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_type_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private TransactionType transactionType;

	public Expenses(Long id, String description, Long amount, @Valid Users user,
			@Valid ExpensesType expenseType,@Valid Accounts account, TransactionType transactionType) {
		super();
		this.expensesId = id;
		this.description = description;
		this.amount = amount;
		this.user = user;
		this.expenseType = expenseType;
		this.account = account;
		this.transactionType = transactionType;

	}

	public Long getId() {
		return expensesId;
	}

	public void setId(Long id) {
		this.expensesId = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public ExpensesType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpensesType expenseType) {
		this.expenseType = expenseType;
	}

	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
}
