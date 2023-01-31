package com.rushikesh.expense_tracker.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "accounts")
public class Accounts {
	
	protected Accounts() {

	}

	@Id
	private Integer id;
	
	@NotBlank
	private String name;
	
	private Users user;
	
	private List<Expenses> expenses;

	public Accounts(Integer id, String name, Users user, List<Expenses> expenses) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.expenses = expenses;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Expenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expenses> expenses) {
		this.expenses = expenses;
	}
	
	
}
