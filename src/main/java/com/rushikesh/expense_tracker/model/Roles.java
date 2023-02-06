package com.rushikesh.expense_tracker.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "roles")
public class Roles {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer rolesId;

	@Column(nullable=false, unique=true)
	@NotEmpty
	private String name;

	@ManyToMany(mappedBy="roles")
	private List<Users> users;

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

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
}
