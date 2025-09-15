package com.stepup.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServiceImpl implements IDatabaseService {

    private String dbUrl = "jdbc:h2:./Office"; // боевой URL по умолчанию

    public DatabaseServiceImpl(String url) {
        this.dbUrl = url;
    }

    @Override
    public void createDB() {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            try (Statement stm = con.createStatement()) {
                // Создание таблиц
                stm.executeUpdate("DROP TABLE IF EXISTS Department");
                stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");

                stm.executeUpdate("DROP TABLE IF EXISTS Employee");
                stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания БД", e);
        }
    }

    // Отдельный метод для заполнения тестовыми данными
    public void fillTestData() {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            try (Statement stm = con.createStatement()) {
                // Добавление начальных отделов
                stm.executeUpdate("INSERT INTO Department VALUES(1,'Accounting')");
                stm.executeUpdate("INSERT INTO Department VALUES(2,'IT')");
                stm.executeUpdate("INSERT INTO Department VALUES(3,'HR')");

                // Добавление начальных сотрудников
                stm.executeUpdate("INSERT INTO Employee VALUES(1,'Pete',1)");
                stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");
                stm.executeUpdate("INSERT INTO Employee VALUES(3,'Liz',2)");
                stm.executeUpdate("INSERT INTO Employee VALUES(4,'Tom',2)");
                stm.executeUpdate("INSERT INTO Employee VALUES(5,'Todd',3)");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка заполнения тестовыми данными", e);
        }
    }

    @Override
    public void addDepartment(Department d) {
        try (Connection con = getConnection();
             PreparedStatement stm = con.prepareStatement("INSERT INTO Department VALUES(?,?)")) {
            stm.setInt(1, d.departmentID);
            stm.setString(2, d.getName());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления отдела", e);
        }
    }

    @Override
    public void removeDepartment(Department d) {
        try (Connection con = getConnection()) {
            // Сначала удаляем сотрудников отдела
            try (PreparedStatement stm1 = con.prepareStatement("DELETE FROM Employee WHERE DepartmentID = ?")) {
                stm1.setInt(1, d.departmentID);
                stm1.executeUpdate();
            }

            // Затем удаляем сам отдел
            try (PreparedStatement stm2 = con.prepareStatement("DELETE FROM Department WHERE ID = ?")) {
                stm2.setInt(1, d.departmentID);
                stm2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления отдела", e);
        }
    }

    @Override
    public void addEmployee(Employee empl) {
        try (Connection con = getConnection();
             PreparedStatement stm = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?)")) {
            stm.setInt(1, empl.getEmployeeId());
            stm.setString(2, empl.getName());
            stm.setInt(3, empl.getDepartmentId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления сотрудника", e);
        }
    }

    @Override
    public void removeEmployee(Employee empl) {
        try (Connection con = getConnection();
             PreparedStatement stm = con.prepareStatement("DELETE FROM Employee WHERE ID = ?")) {
            stm.setInt(1, empl.getEmployeeId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления сотрудника", e);
        }
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    // Дополнительные методы для тестирования
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Department")) {
            while (rs.next()) {
                Department d = new Department(
                        rs.getInt("ID"),
                        rs.getString("NAME")
                );
                departments.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка отделов", e);
        }
        return departments;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Employee")) {
            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("DepartmentID")
                );
                employees.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка сотрудников", e);
        }
        return employees;
    }
}

