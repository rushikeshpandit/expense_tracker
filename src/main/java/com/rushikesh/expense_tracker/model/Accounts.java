package com.rushikesh.expense_tracker.model;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "accounts")
public class Accounts extends Audit {

	public Accounts() {
	}
	
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "account_id", nullable = false)
	private Long id;
	
	@NotBlank
	@Column(name = "account_name", nullable = false)
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Users user;
	
	@OneToMany(mappedBy = "id")
//	@ElementCollection(targetClass=Expenses.class)
	@JsonIgnore
	private List<Expenses> expenses;

	public Accounts(Long id, String name, Users user, List<Expenses> expenses) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.expenses = expenses;
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
