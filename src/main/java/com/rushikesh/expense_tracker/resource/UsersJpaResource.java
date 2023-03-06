package com.rushikesh.expense_tracker.resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/api")
public class UsersJpaResource implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	public List<Users> GetUsers() {
		List<Users> allUsers = userRepository.findAll();
		return allUsers;
	}
	
	@GetMapping("/user/{id}")
	public Users GetUser(@PathVariable int id) {
		Optional<Users> user = userRepository.findById(id);
		return user.get();
	}

	//	@PostMapping("/users")
	//	public Users createUser(@RequestBody Users user) {
	//
	//		userRepository.save(user);
	//		return user;
	//	}
	//
	//	@DeleteMapping("/users")
	//	public ResponseEntity<Void> deleteExpense() {
	//		userRepository.deleteAll();
	//		return ResponseEntity.noContent().build();
	//	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return Users.build(user);
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Users user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

}
