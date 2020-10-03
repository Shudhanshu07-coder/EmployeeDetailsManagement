package com.example.employee.EmployeeDetails.controller;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.EmployeeDetails.exception.ResourceAlreadyExists;
import com.example.employee.EmployeeDetails.exception.ResourceNotFound;
import com.example.employee.EmployeeDetails.model.AuthenticationRequest;
import com.example.employee.EmployeeDetails.model.AuthenticationResponse;
import com.example.employee.EmployeeDetails.model.Employee;
import com.example.employee.EmployeeDetails.service.EmployeeService;
import com.example.employee.EmployeeDetails.service.MyUserDetailsService;
import com.example.employee.EmployeeDetails.utils.JwtTokenUtil;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id) throws ResourceNotFound {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employees")
    public Object addEmployees(@RequestBody @Valid Employee employee, Errors errors) throws ResourceAlreadyExists {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            return employeeService.addEmployee(employee);
        } catch (Exception ex) {
            return new ResponseEntity<>("Email id already exists", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/employees/{id}")
    public Object updateEmployee(@RequestBody @Valid Employee e, @PathVariable int id, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return employeeService.updateEmployee(e, id);
    }

    @DeleteMapping("/employees")
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployees();
    }

    @DeleteMapping("employees/{id}")
    public void deleteEmployeeByID(@RequestBody Employee e, @PathVariable int id) {
        employeeService.deleteEmployeeByID(id);
    }

    @PatchMapping("employees/{id}")
    public Object patchEmployeeByID(@RequestBody @Valid Employee e, @PathVariable int id, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return employeeService.patchEmployee(e, id);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
