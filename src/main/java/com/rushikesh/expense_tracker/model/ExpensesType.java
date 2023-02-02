package com.rushikesh.expense_tracker.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "expenses_type")
public class ExpensesType extends Audit {
	
	public ExpensesType() {
	}
	
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@NotBlank
	@Column(name = "expenses_type_name", nullable = false)
	private String expenseTypeName;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @JsonIgnore
	private Users user;

	public ExpensesType(Long id, String expenseTypeName, Users user) {
		super();
		this.id = id;
		this.expenseTypeName = expenseTypeName;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
