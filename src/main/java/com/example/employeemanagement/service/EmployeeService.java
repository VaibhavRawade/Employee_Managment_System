package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** This class represents the service for employees. */
@Service
public class EmployeeService {

  /** The employee repository. */
  @Autowired private EmployeeRepository employeeRepository;


  /** The JPA entity manager, used to flush and clear the persistence context after save. */
  @Autowired private EntityManager entityManager;
  
  @Autowired private UserRepository userRepository;

  
  public User validateUser(String username, String password) 
  {

	    Optional<User> optionalUser = userRepository.findByUsername(username);

	    if (!optionalUser.isPresent()) {
	        return null;
	    }

	    User user = optionalUser.get();

	    if (!user.getPassword().equals(password)) {
	        return null;
	    }

	    return user;
	}
  
  /**
   * Get all employees.
   *
   * @return List of all employees
   */
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAllWithDepartments();
  }

  /**
   * Get employee by ID.
   *
   * @param id ID of the employee to be retrieved
   * @return Employee with the specified ID
   */
  public Optional<Employee> getEmployeeById(Long id)
  {
	  
	  Optional<Employee> emp= employeeRepository.findByIdWithDepartment(id);
	//  System.out.println(emp.get().getImageName());
	  
	  
	  return emp;
  }

  /**
   * Save an employee.
   *
   * @param employee Employee to be saved
   * @return Saved employee
   */
  @Transactional
  public Employee saveEmployee(Employee employee) {
	  Role r=new Role();
	  r.setId(2L);
	  
	  employee.setRole(r);
    Employee saved = employeeRepository.save(employee);
    entityManager.flush();
    entityManager.clear();
    return employeeRepository.findByIdWithDepartment(saved.getId()).orElse(saved);
  }

  /**
   * Deletes an employee by their ID.
   *
   * @param id the ID of the employee to delete
   */
  public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
  }
}
