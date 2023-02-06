package com.rushikesh.expense_tracker.resource;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.constants.ConstantUtil;
import com.rushikesh.expense_tracker.model.Roles;
import com.rushikesh.expense_tracker.model.Users;
import com.rushikesh.expense_tracker.payload.request.LoginRequest;
import com.rushikesh.expense_tracker.payload.request.SignupRequest;
import com.rushikesh.expense_tracker.payload.response.ServiceResponse;
import com.rushikesh.expense_tracker.payload.response.UserInfoResponse;
import com.rushikesh.expense_tracker.repository.RolesRepository;
import com.rushikesh.expense_tracker.repository.UserRepository;
import com.rushikesh.expense_tracker.security.JwtUtils;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthUsersJpaResource {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolesRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Users userDetails = (Users) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(new UserInfoResponse(userDetails.getId(),
						userDetails.getUsername(),
						userDetails.getEmail(),
						roles,
						jwtCookie.getValue()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		ServiceResponse response = new ServiceResponse();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			response.setErrorMessage("Error: Username is already taken!");
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			return ResponseEntity
					.badRequest()
					.body(response);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			response.setErrorMessage("Error: Email is already in use!");
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			return ResponseEntity
					.badRequest()
					.body(response);
		}

		// Create new user's account
		Users user = new Users(null, signUpRequest.getUsername(), 
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), null, null, null);

		List<String> strRoles = signUpRequest.getRoles();
		Set<Roles> newRoles = new HashSet<>();
		if (strRoles == null) {
			Roles userRole = roleRepository.findByName(ConstantUtil.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			newRoles.add(userRole);
		} else {
			strRoles.forEach(roles -> {
				switch (roles) {
				case "admin":
					Roles adminRole = roleRepository.findByName(ConstantUtil.ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					newRoles.add(adminRole);
					break;

				default:
					Roles userRole = roleRepository.findByName(ConstantUtil.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					newRoles.add(userRole);
				}
			});
		}

		List<Roles> updatedRoles = new ArrayList<>(newRoles);

		user.setRoles(updatedRoles);
		userRepository.save(user);
		response.setReturnObject(user);
		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		return ResponseEntity
				.ok()
				.body(response);

	}

}
