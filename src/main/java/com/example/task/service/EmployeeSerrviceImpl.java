package com.example.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.task.model.Employee;
import com.example.task.repo.EmployeeRepo;

@Service
public class EmployeeSerrviceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public String save(Employee e) {
		try {			
			employeeRepo.save(e);
			return "Saved!";
		} catch (Exception E) {
			E.printStackTrace();
			return "Something Went Wrong!";
		}
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> list = null;
		try {			
			list=employeeRepo.findAll();
			return list;
		} catch (Exception E) {
			E.printStackTrace();
		}
		return list;
	}

}
