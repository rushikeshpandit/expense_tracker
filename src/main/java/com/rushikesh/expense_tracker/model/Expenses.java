package com.rushikesh.expense_tracker.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@Document(collection = "expenses")
public class Expenses {
	
	@Id
	private Integer id;
	
	private String description;
	
	@PositiveOrZero
	private Long amount;
	
	private Users user;
	
	private ExpensesType expenseType;

	private Accounts account;
	
	private Date  createAt;

	public Expenses(Integer id, String description, Long amount, @Valid Users user,
			@Valid ExpensesType expenseType,@Valid Accounts account,  Date createAt) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.user = user;
		this.expenseType = expenseType;
		this.account = account;
		this.createAt = createAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
}
