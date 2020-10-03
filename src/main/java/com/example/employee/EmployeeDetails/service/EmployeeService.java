package com.example.employee.EmployeeDetails.service;

import javax.validation.ConstraintViolationException;

import com.example.employee.EmployeeDetails.exception.ResourceAlreadyExists;
import com.example.employee.EmployeeDetails.exception.ResourceNotFound;
import com.example.employee.EmployeeDetails.model.Employee;

public interface EmployeeService {

    Employee getEmployee(int id) throws ResourceNotFound;

    Employee addEmployee(Employee employee) throws ResourceAlreadyExists,ConstraintViolationException;

    Employee updateEmployee(Employee e, int id);

    void deleteAllEmployees();

    void deleteEmployeeByID(int id);

    Employee patchEmployee(Employee e, int id);

}
