package com.example.employee.EmployeeDetails.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.EmployeeDetails.exception.ResourceAlreadyExists;
import com.example.employee.EmployeeDetails.exception.ResourceNotFound;
import com.example.employee.EmployeeDetails.model.Employee;
import com.example.employee.EmployeeDetails.repository.EmployeeRepository;
import com.example.employee.EmployeeDetails.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee getEmployee(int id) throws ResourceNotFound {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new ResourceNotFound();
        }
    }

    @Transactional
    public Employee addEmployee(Employee employee) throws ResourceAlreadyExists, ConstraintViolationException {
        try {
            getEmployee(employee.getEmployeeID());
            throw new ResourceAlreadyExists();
        } catch (ResourceNotFound e) {
            return employeeRepository.save(employee);
        }
    }

    @Transactional
    public Employee updateEmployee(Employee employee, int id) {
        if (id == employee.getEmployeeID()) {
            return employeeRepository.save(employee);
        }
        return employee;
    }

    @Transactional
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Transactional
    public void deleteEmployeeByID(int id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee patchEmployee(Employee employee, int id) {
        if (id == employee.getEmployeeID()) {
            return employeeRepository.save(employee);
        }
        return employee;
    }

}
