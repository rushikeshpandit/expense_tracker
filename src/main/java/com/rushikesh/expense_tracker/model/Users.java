package com.rushikesh.expense_tracker.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "users")
public class Users {


	@Id
	private Integer id;

	@NotBlank
	private String name;

	private List<Accounts> accounts;
	

	private List<ExpensesType> expensesType;

	public Users(Integer id,  String name,
			List<Accounts> accounts, List<ExpensesType> expensesType) {
		super();
		this.id = id;
		this.name = name;
		this.accounts = accounts;
		this.expensesType = expensesType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Accounts> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Accounts> accounts) {
		this.accounts = accounts;
	}

	public List<ExpensesType> getExpensesType() {
		return expensesType;
	}

	public void setExpensesType(List<ExpensesType> expensesType) {
		this.expensesType = expensesType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", accounts=" + accounts + "]";
	}

}

