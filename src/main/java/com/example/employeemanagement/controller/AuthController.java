package com.example.employeemanagement.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.dto.AuthRequestDto;
import com.example.employeemanagement.dto.EmployeeLoginRequest;
import com.example.employeemanagement.dto.ResetPasswordRequestDto;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.security.JwtTokenUtil;
import com.example.employeemanagement.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/** This class represents the REST API controller for user authentication. */
@RestController
@Tag(name = "Authentication APIs", description = "API Operations related to user authentication")
public class AuthController {

  /** The Spring Security authentication manager used to verify credentials. */
  @Autowired private AuthenticationManager authenticationManager;

  /** The service that loads user-specific data for authentication. */
  @Autowired private UserDetailsService userDetailsService;

  /** The repository for persisting and querying {@link User} entities. */
  @Autowired private UserRepository userRepository;

  /** The encoder used to hash and verify passwords. */
//  @Autowired private PasswordEncoder passwordEncoder;

  /** Utility for generating and validating JWT tokens. */
  @Autowired private JwtTokenUtil jwtTokenUtil;

  
  @Autowired private EmployeeService employeeService;

  /**
   * Register user API.
   *
   * @param request The registration details
   * @return Success message
   */
  @Operation(summary = "Register user", description = "Register a new user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "409", description = "Username already exists"),
        @ApiResponse(responseCode = "500", description = "Unable to register user")
      })
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequestDto request) {
    try {
      User user = new User();
      user.setUsername(request.getUsername());
      user.setPassword(request.getPassword()); ///passwordEncoder.encode(
      Role role=new Role();
      role.setId(1l);
      user.setRole(role);
      
      userRepository.save(user); 
     
      
      return ResponseEntity.ok("User registered successfully!");
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already exists");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: Unable to register user");
    }
  }

  /**
   * Authenticate user API.
   *
   * @param request The login credentials
   * @return JWT token
   */
  
  
  @Operation(
      summary = "Authenticate user",
      description = "Authenticate a user and generate a JWT token")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid username or password"),
        @ApiResponse(responseCode = "500", description = "Unable to authenticate user")
      })
	  @PostMapping("/authenticate")
	  public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthRequestDto request) 
	  {
	      System.out.println(" ->       "+ request.toString());
	
	    try
	    {
	    	/*
	      authenticationManager.authenticate(
	          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
	*/
	    	
	    	User user = employeeService.validateUser(
	    	        request.getUsername(),
	    	        request.getPassword());
	    	
	    	if (user == null) {
	    	    return ResponseEntity
	    	            .status(HttpStatus.UNAUTHORIZED)
	    	            .body("Invalid username or password");
	    	}
	    	
	      System.out.println(" 1->       "+ request.toString());
	      final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	      final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());
	
	      Map<String, String> response = new HashMap<>();
	      response.put("token", jwt);
	      response.put("roleId", user.getId().toString());
	      response.put("roleName", user.getRole().getRoleName());
	      return ResponseEntity.ok(response);
	
	    } 
	    catch (BadCredentialsException e) 
	    {
	    	System.out.print(e);
	    	
	      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	          .body("Error: Invalid username or password");
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body("Error: Unable to authenticate");
	    }
	  }

  /**
   * Verify if a username exists.
   *
   * @param username The username to verify
   * @return Response message indicating whether the username exists
   */
  @Operation(summary = "Verify username", description = "Verify if a username exists in the system")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Username exists"),
        @ApiResponse(responseCode = "404", description = "Username not found")
      })
  @GetMapping("/verify-username/{username}")
  public ResponseEntity<?> verifyUsername(@PathVariable String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return ResponseEntity.ok("Username exists");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Username not found");
    }
  }

  /**
   * Reset password for a given username.
   *
   * @param request The reset password details
   * @return Response message indicating success or failure
   */
  @Operation(summary = "Reset password", description = "Reset the password for the given username")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Password reset successfully"),
        @ApiResponse(responseCode = "404", description = "Username not found")
      })
  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
    Optional<User> user = userRepository.findByUsername(request.getUsername());

    if (user.isPresent()) {
      User existingUser = user.get();
      existingUser.setPassword(request.getNewPassword());
      userRepository.save(existingUser);
      return ResponseEntity.ok("Password reset successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Username not found");
    }
  }
  
  
  
  
  
  
  
  
  
  

	
	@Autowired private EmployeeRepository employeeRepository;
	
	@PostMapping("/employee/authenticate")
	public ResponseEntity<?> employeeLogin(
	        @Valid @RequestBody EmployeeLoginRequest request) 
	{

	    try
	    {

	        Employee employee = employeeRepository
	                .findByEmail(request.getEmail())
	                .orElseThrow(() ->
	                        new UsernameNotFoundException("Employee not found"));

	        if (
	                !request.getPassword().equals(
	                employee.getPassword())) 
	        {
	        	
	        	System.out.println(request.getPassword()+"  "+employee.getPassword());
	        	
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body("Invalid email or password");
	        }

	        String jwt = jwtTokenUtil.generateToken(employee.getEmail());

	        Map<String, Object> response = new HashMap<>();
	        response.put("token", jwt);
	        response.put("roleId", employee.getRole().getId());
	        response.put("empId", employee.getId());
	        response.put("roleName", employee.getRole().getRoleName());

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error: Unable to authenticate employee");
	    }
	}

}
