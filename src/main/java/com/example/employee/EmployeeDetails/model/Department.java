package com.example.employee.EmployeeDetails.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "department")
@Data
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int departmentId;

    @Column(name = "department_name")
    private String departmentName;

    public Department() {

    }

    public Department(int departmentId, String departmentName) {
        super();
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (departmentId != other.departmentId)
            return false;
        if (departmentName == null) {
            if (other.departmentName != null)
                return false;
        } else if (!departmentName.equals(other.departmentName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + departmentId;
        result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
        return result;
    }

}
