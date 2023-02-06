package com.rushikesh.expense_tracker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Users extends Audit implements UserDetails {

	public Users() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long usersId;

	@NotBlank
	@Column(name = "user_name", nullable = false)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	@JsonIgnore
	private String password;


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("users")
	@ElementCollection(targetClass=Accounts.class)
	@JsonIgnore
	private Collection<Accounts> accounts = new ArrayList<Accounts>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("users")
	@ElementCollection(targetClass=ExpensesType.class)
	@JsonIgnore
	private Collection<ExpensesType> expensesType = new ArrayList<ExpensesType>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Roles> roles = new HashSet<>();

	private Collection<? extends GrantedAuthority> authorities;


	public Users(Long id,  String name, String email, String password,
			Collection<Accounts> accounts, Collection<ExpensesType> expensesType, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.usersId = id;
		this.username = name;
		this.email = email;
		this.password = password;
		this.accounts = accounts;
		this.expensesType = expensesType;
		this.authorities = authorities;
	}

	public static Users build(Users user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());

		return new Users(
				user.getId(), 
				user.getUserName(), 
				user.getEmail(),
				user.getPassword(), 
				user.getAccounts(),
				user.getExpensesType(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return usersId;
	}

	public void setId(Long id) {
		this.usersId = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Accounts> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Accounts> accounts) {
		this.accounts = accounts;
	}

	public Collection<ExpensesType> getExpensesType() {
		return expensesType;
	}

	public void setExpensesType(Collection<ExpensesType> expensesType) {
		this.expensesType = expensesType;
	}

	public  Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles( Set<Roles> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + usersId + ", name=" + username + ", accounts=" + accounts + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Users user = (Users) o;
		return Objects.equals(usersId, user.usersId);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}

