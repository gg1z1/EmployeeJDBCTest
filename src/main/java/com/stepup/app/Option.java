package com.stepup.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public enum Option {

    // Константы перечисления
    ADD_EMPLOYEE {
        @Override
        public String getText() {
            return this.ordinal() + ".Добавить сотрудника";
        }

        @Override
        public void action(Service service) {
            try {
                System.out.print("Введите ID сотрудника: ");
                int id = getIntInput();
                System.out.print("Введите имя сотрудника: ");
                String name = getStringInput();
                System.out.print("Введите ID отдела: ");
                int depId = getIntInput();
                service.addEmployee(new Employee(id, name, depId));
            } catch (Exception e) {
                System.out.println("Ошибка при добавлении сотрудника: " + e.getMessage());
            }
        }
    },

    DELETE_EMPLOYEE {
        @Override
        public String getText() {
            return this.ordinal() + ".Удалить сотрудника";
        }

        @Override
        public void action(Service service) {
            try {
                System.out.print("Введите ID сотрудника: ");
                int id = getIntInput();
                service.removeEmployee(new Employee(id, "", 0));
            } catch (Exception e) {
                System.out.println("Ошибка при удалении сотрудника: " + e.getMessage());
            }
        }
    },

    ADD_DEPARTMENT {
        @Override
        public String getText() {
            return this.ordinal() + ".Добавить отдел";
        }

        @Override
        public void action(Service service) {
            try {
                System.out.print("Введите ID отдела: ");
                int id = getIntInput();
                System.out.print("Введите название отдела: ");
                String name = getStringInput();
                service.addDepartment(new Department(id, name));
            } catch (Exception e) {
                System.out.println("Ошибка при добавлении отдела: " + e.getMessage());
            }
        }
    },

    DELETE_DEPARTMENT {
        @Override
        public String getText() {
            return this.ordinal() + ".Удалить отдел";
        }

        @Override
        public void action(Service service) {
            try {
                System.out.print("Введите ID отдела: ");
                int id = getIntInput();
                service.removeDepartment(new Department(id, ""));
            } catch (Exception e) {
                System.out.println("Ошибка при удалении отдела: " + e.getMessage());
            }
        }
    },

    CLEAR_DB {
        @Override
        public String getText() {
            return this.ordinal() + ".Сбросить базу данных";
        }

        @Override
        public void action(Service service) {
            service.createDB();
        }
    },

    PRINT_DEPARTMENTS {
        @Override
        public String getText() {
            return this.ordinal() + ".Вывести все отделы";
        }

        @Override
        public void action(Service service) {
            List<Department> departments = service.getAllDepartments();
            printDepartments(departments);
        }
    },

    PRINT_EMPLOYEES {
        @Override
        public String getText() {
            return this.ordinal() + ".Вывести всех сотрудников";
        }

        @Override
        public void action(Service service) {
            List<Employee> employees = service.getAllEmployees();
            List<Department> departments = service.getAllDepartments();
            printEmployees(employees, departments);
        }
    },

    FILL_TEST_DATA {
        @Override
        public String getText() {
            return this.ordinal() + ".Заполнить базу тестовыми данными";
        }

        @Override
        public void action(Service service) {
            try {
                // Проверяем, пуста ли база
                if (service.getAllEmployees().isEmpty() &&
                        service.getAllDepartments().isEmpty()) {

                    service.fillTestData();
                    System.out.println("Тестовые данные успешно добавлены");
                } else {
                    System.out.println("База уже содержит данные. " +
                            "Используйте опцию \"Сбросить базу данных\" перед заполнением.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка при заполнении тестовыми данными: " + e.getMessage());
            }
        }
    },

    EXIT {
        @Override
        public String getText() {
            return this.ordinal() + ".Выход";
        }

        @Override
        public void action(Service service) {
            System.out.println("Good Bye America!");
        }
    };

    private final Scanner scanner = new Scanner(System.in);

    public abstract String getText();
    public abstract void action(Service service);

    int getIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер
            return value;
        } catch (Exception e) {
            System.out.println("Ошибка при вводе числа. Попробуйте еще раз.");
            scanner.nextLine(); // Очищаем некорректный ввод
            return getIntInput(); // Повторный запрос
        }
    }

    String getStringInput() {
        try {
            return scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Ошибка при вводе строки. Попробуйте еще раз.");
            return getStringInput(); // Повторный запрос
        }
    }

    void printDepartments(List<Department> departments) {
        if (departments.isEmpty()) {
            System.out.println("Отделы не найдены");
            return;
        }

        System.out.println("\nСписок отделов:");
        System.out.println("--------------------------------");
        System.out.printf("%-5s | %-20s%n", "ID", "Название отдела");
        System.out.println("--------------------------------");

        for (Department dept : departments) {
            System.out.printf("%-5d | %-20s%n",
                    dept.getDepartmentID(),
                    dept.getName());
        }

        System.out.println("--------------------------------");
    }

    void printEmployees(List<Employee> employees, List<Department> departments) {
        if (employees.isEmpty()) {
            System.out.println("Сотрудники не найдены");
            return;
        }

        System.out.println("\nСписок сотрудников:");
        System.out.println("------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-20s%n",
                "ID", "Имя сотрудника", "Отдел");
        System.out.println("------------------------------------------------");

        for (Employee emp : employees) {
            String departmentName = "Не назначен";
            for (Department dept : departments) {
                if (dept.getDepartmentID() == emp.getDepartmentId()) {
                    departmentName = dept.getName();
                    break;
                }
            }

            System.out.printf("%-5d | %-20s | %-20s%n",
                    emp.getEmployeeId(),
                    emp.getName(),
                    departmentName);
        }

        System.out.println("------------------------------------------------");
    }

}