package com.rushikesh.expense_tracker.model;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@DynamicUpdate
@Table(name = "accounts")
public class Accounts extends Audit {

	public Accounts() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long accountId;

	@NotBlank
	@Column(name = "account_name", nullable = false)
	private String name;

	@Column(name = "balance", nullable = false)
	private Long balance;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	@JsonIgnoreProperties("accounts")
	@JsonIgnore
	private Users user;

	@ElementCollection(targetClass=Expenses.class)	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.MERGE)
	@JsonIgnoreProperties("account")
	private List<Expenses> expenses;

	public Accounts(Long id, String name, Long balance, Users user, List<Expenses> expenses) {
		super();
		this.accountId = id;
		this.name = name;
		this.user = user;
		this.expenses = expenses;
		this.balance = balance;
	}

	public Long getId() {
		return accountId;
	}

	public void setId(Long id) {
		this.accountId = id;
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

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}
