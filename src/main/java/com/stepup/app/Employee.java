package com.stepup.app;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Objects;

public class Employee {
    private int employeeId;
    private String name;
    private int departmentId;

    public Employee(int employeeId, String name, int departmentId) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
    }

    public int getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.employeeId;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + this.departmentId;
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Employee other = (Employee)obj;
            if (this.employeeId != other.employeeId) {
                return false;
            } else if (this.departmentId != other.departmentId) {
                return false;
            } else {
                return Objects.equals(this.name, other.name);
            }
        }
    }

    public String toString() {
        return "Employee{employeeId=" + this.employeeId + ", name=" + this.name + ", departmentId=" + this.departmentId + '}';
    }
}
