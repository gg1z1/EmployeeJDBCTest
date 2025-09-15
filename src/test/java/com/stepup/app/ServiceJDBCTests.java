package com.stepup.app;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceJDBCTests {
    private Service service;
    private DatabaseServiceImpl dbService;

    @BeforeEach
    public void setUp() {
        // Создаем сервис с тестовым URL
        dbService = new DatabaseServiceImpl("jdbc:h2:./test_office");
        service = new Service(dbService);
        service.createDB();
    }

    @AfterEach
    public void tearDown() {
        // Очищаем тестовую БД после каждого теста
        service.createDB();
    }

    @Test
    public void testCreateDB() {
        // Создаем соединение с базой данных
        try (Connection con = dbService.getConnection()) {
            // Получаем метаданные базы данных
            DatabaseMetaData meta = con.getMetaData();

            // Проверяем существование таблицы Department
            ResultSet rs = meta.getTables(null, null, "DEPARTMENT", new String[]{"TABLE"});
            assertTrue(rs.next(), "Таблица DEPARTMENT не существует");

            // Проверяем существование таблицы Employee
            rs = meta.getTables(null, null, "EMPLOYEE", new String[]{"TABLE"});
            assertTrue(rs.next(), "Таблица EMPLOYEE не существует");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при проверке создания БД", e);
        }
    }

    @Test
    public void testAddDepartment() {
        Department department = new Department(1, "Test Department");
        service.addDepartment(department);

        // Проверяем добавление через прямой запрос
        try (Connection con = dbService.getConnection()) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Department WHERE ID = 1");
                assertTrue(rs.next());
                assertEquals(department.departmentID, rs.getInt("ID"));
                assertEquals(department.name, rs.getString("NAME"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemoveDepartment() {
        Department department = new Department(1, "Test Department");
        Employee employee = new Employee(1, "Test", 1);

        // Добавляем данные
        service.addDepartment(department);
        service.addEmployee(employee);

        // Удаляем отдел
        service.removeDepartment(department);

        // Проверяем удаление
        try (Connection con = dbService.getConnection()) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Department WHERE ID = 1");
                assertFalse(rs.next());

                rs = stm.executeQuery("SELECT * FROM Employee WHERE ID = 1");
                assertFalse(rs.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee(1, "Test", 1);
        service.addEmployee(employee);

        try (Connection con = dbService.getConnection()) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Employee WHERE ID = 1");
                assertTrue(rs.next());
                assertEquals(employee.getEmployeeId(), rs.getInt("ID"));
                assertEquals(employee.getName(), rs.getString("NAME"));
                assertEquals(employee.getDepartmentId(), rs.getInt("DepartmentID"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemoveEmployee() {
        Employee employee = new Employee(1, "Test", 1);
        service.addEmployee(employee);
        service.removeEmployee(employee);

        try (Connection con = dbService.getConnection()) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Employee WHERE ID = 1");
                assertFalse(rs.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
