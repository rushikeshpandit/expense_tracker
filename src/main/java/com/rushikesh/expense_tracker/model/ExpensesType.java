package com.rushikesh.expense_tracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "expensestypes")
public class ExpensesType {
	
	@Id
	private Integer id;
	
	@NotBlank
	private String expenseTypeName;

	private Users user;

	public ExpensesType(Integer id, String expenseTypeName, Users user) {
		super();
		this.id = id;
		this.expenseTypeName = expenseTypeName;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
