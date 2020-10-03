package com.example.employee.EmployeeDetails.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeID;

    @NotEmpty(message = "First name must not be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be a valid email address")
    @Column(name = "email",unique = true)
    private String email;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {

    }

    public Employee(int employeeID, String firstName, String lastName, String email, Department department) {
        super();
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (employeeID != other.employeeID)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + employeeID;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

}