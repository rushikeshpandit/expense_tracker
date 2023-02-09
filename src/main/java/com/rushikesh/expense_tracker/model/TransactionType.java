package com.rushikesh.expense_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "transaction_type")
public class TransactionType {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer transactionTypeId;

	@NotBlank
	@Column(name = "transaction_type_name", nullable = false)
	private String name;


	public TransactionType() {

	}

	public TransactionType(String name) {
		this.name = name;
	}

	public Integer getId() {
		return transactionTypeId;
	}

	public void setId(Integer id) {
		this.transactionTypeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
