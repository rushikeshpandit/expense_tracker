package com.rushikesh.expense_tracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class Users extends Audit {

	
	public Users() {
	}

	@Id
	@SequenceGenerator(name = "user_sequence", 
    sequenceName = "user_sequence", 
    initialValue = 1, allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@Column(name = "user_id", nullable = false)
    private Long id;

	@NotBlank
	@Column(name = "user_name", nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @JsonIgnore
    @ElementCollection(targetClass=Accounts.class)
	private List<Accounts> accounts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @JsonIgnore
    @ElementCollection(targetClass=ExpensesType.class)
	private List<ExpensesType> expensesType;

	public Users(Long id,  String name,
			List<Accounts> accounts, List<ExpensesType> expensesType) {
		super();
		this.id = id;
		this.name = name;
		this.accounts = accounts;
		this.expensesType = expensesType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

