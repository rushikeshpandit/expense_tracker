package com.rushikesh.expense_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "roles")
public class Roles {
	@Id
	private Integer rolesId;

	@Column(length = 20)
	private String name;

	public Roles() {

	}

	public Roles(String name) {
		this.name = name;
	}

	public Integer getId() {
		return rolesId;
	}

	public void setId(Integer id) {
		this.rolesId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
