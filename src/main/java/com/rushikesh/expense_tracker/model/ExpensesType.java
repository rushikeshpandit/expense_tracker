package com.rushikesh.expense_tracker.model;


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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "expenses_type")
public class ExpensesType extends Audit {

	public ExpensesType() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long expensesTypeId;

	@NotBlank
	@Column(name = "expenses_type_name", nullable = false)
	private String expenseTypeName;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	@JsonIgnoreProperties("expensesType")
	@JsonIgnore
	private Users user;

	public ExpensesType(Long id, String expenseTypeName, Users user) {
		super();
		this.expensesTypeId = id;
		this.expenseTypeName = expenseTypeName;
		this.user = user;
	}

	public Long getId() {
		return expensesTypeId;
	}

	public void setId(Long id) {
		this.expensesTypeId = id;
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
