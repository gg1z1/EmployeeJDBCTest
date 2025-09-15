package com.stepup.app;

import java.util.List;

public interface IDatabaseService {
    void addDepartment(Department d);
    void removeDepartment(Department d);
    void addEmployee(Employee e);
    void removeEmployee(Employee e);
    void createDB();
    void fillTestData();
    List<Employee> getAllEmployees();
    List<Department> getAllDepartments();
}
