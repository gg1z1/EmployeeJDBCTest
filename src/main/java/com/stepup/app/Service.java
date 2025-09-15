package com.stepup.app;

import java.util.List;

public class Service {
    private final IDatabaseService databaseService;

    // Конструктор для внедрения зависимости
    public Service(IDatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Методы делегируют работу в IDatabaseService

    public void createDB() {
        databaseService.createDB();
    }

    public void addDepartment(Department department) {
        databaseService.addDepartment(department);
    }

    public void removeDepartment(Department department) {
        databaseService.removeDepartment(department);
    }

    public void addEmployee(Employee employee) {
        databaseService.addEmployee(employee);
    }

    public void removeEmployee(Employee employee) {
        databaseService.removeEmployee(employee);
    }

    public List<Employee> getAllEmployees() {
        return databaseService.getAllEmployees();
    }

    public List<Department> getAllDepartments() {
        return databaseService.getAllDepartments();
    }

    public void fillTestData() {
        databaseService.fillTestData();
    }

    // Дополнительные методы бизнес-логики можно добавить здесь
    // Например:
    /*public boolean isEmployeeExists(int employeeId) {
        List<Employee> employees = getAllEmployees();
        for (Employee emp : employees) {
            if (emp.getEmployeeId() == employeeId) {
                return true;
            }
        }
        return false;
    }*/
}
