package com.rushikesh.expense_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "expenses")
public class Expenses extends Audit {
	
	public Expenses() {
	}
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "expense_id", nullable = false)
    private Long id;
	
	@Column(name = "expense_description", nullable = false)
	private String description;
	
	@PositiveOrZero
	@Column(name = "expense_amount", nullable = false)
	private Long amount;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
	private Users user;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_type_id", nullable = false)
    @JsonIgnore
	private ExpensesType expenseType;

//	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @JsonIgnore
//	@Column(name = "expense_id", nullable = false)
	private Accounts account;

	public Expenses(Long id, String description, Long amount, @Valid Users user,
			@Valid ExpensesType expenseType,@Valid Accounts account) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.user = user;
		this.expenseType = expenseType;
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
}
