package com.example.task.service;

import java.util.List;

import com.example.task.model.Employee;

public interface EmployeeService {

	public String save(Employee e);
	
	public List<Employee> getEmployees();
}
