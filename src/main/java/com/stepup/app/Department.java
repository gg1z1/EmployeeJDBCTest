package com.stepup.app;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Objects;

public class Department {
    int departmentID;
    String name;

    public Department(int departmentID, String name) {
        this.departmentID = departmentID;
        this.name = name;
    }

    public int getDepartmentID() {
        return this.departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.departmentID;
        hash = 71 * hash + Objects.hashCode(this.name);
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
            Department other = (Department)obj;
            if (this.departmentID != other.departmentID) {
                return false;
            } else {
                return Objects.equals(this.name, other.name);
            }
        }
    }

    public String toString() {
        return "Department{departmentID=" + this.departmentID + ", name=" + this.name + '}';
    }
}

