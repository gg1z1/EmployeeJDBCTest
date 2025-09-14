package com.stepup;

import java.sql.*;

public class EmployeeJDBCTests {

    private static final String URL = "jdbc:h2:C:/Users/artim/Downloads/Office/Office";

    // Метод для получения подключения к базе данных
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {

            System.out.println("\nМетаданные таблицы Employee:");
            getTableMetadata("Employee".toUpperCase());
            System.out.println("\nМетаданные таблицы DEPARTMENT:");
            getTableMetadata("DEPARTMENT".toUpperCase());

            // Задача 1: Найти ID сотрудника Ann и установить департамент в HR
            findAndUpdateAnnDepartment(connection);

            // Задача 2: Исправить имена сотрудников с маленькой буквы
            correctEmployeeNames(connection);

            // Задача 3: Подсчет сотрудников в IT-отделе
            //int itEmployeesCount = countITEmployees(connection);
            countITEmployees(connection);

        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
        }
    }

    public static void getTableMetadata(String tableName) throws SQLException {
        try (Connection conn = getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getColumns(null, null, tableName.toUpperCase(), "%")) {
                while (rs.next()) {
                    System.out.println("Column Name: " + rs.getString("COLUMN_NAME"));
                    System.out.println("Type: " + rs.getString("TYPE_NAME"));
                    System.out.println("Size: " + rs.getInt("COLUMN_SIZE"));
                    System.out.println("------------------------");
                }
            }
        }
    }


    private static void findAndUpdateAnnDepartment(Connection connection) throws SQLException {
        String findAnnSql = "SELECT ID FROM Employee WHERE NAME = 'Ann'";
        String updateDepartmentSql = "UPDATE Employee SET DEPARTMENTID = (SELECT ID FROM Department WHERE NAME = 'HR') WHERE ID = ?";

        try (Statement findStatement = connection.createStatement();
             ResultSet resultSet = findStatement.executeQuery(findAnnSql)) {

            int count = 0;
            int annId = 0;

            while (resultSet.next()) {
                count++;
                annId = resultSet.getInt("ID");
            }

            if (count == 1) {
                try (PreparedStatement updateStatement = connection.prepareStatement(updateDepartmentSql)) {
                    updateStatement.setInt(1, annId);
                    updateStatement.executeUpdate();
                    System.out.println("Отдел сотрудника Ann успешно обновлен на HR");
                }
            } else {
                System.out.println("Сотрудник Ann не найден или найдено несколько записей");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении отдела: " + e.getMessage());
        }
    }

    private static void correctEmployeeNames(Connection connection) throws SQLException {
        //String sql = "SELECT id, name FROM Employee WHERE name LIKE '[a-z]%'";
        String findNamesSql = "SELECT ID, NAME FROM Employee";
        String updateNameSql = "UPDATE Employee SET NAME = ? WHERE ID = ?";
        String findFirstEmployeeSql = "SELECT ID, NAME FROM Employee ORDER BY ID LIMIT 1";

        int correctedCount = 0;

        // Проверяем, есть ли хотя бы один сотрудник
        try (Statement checkStatement = connection.createStatement();
             ResultSet firstEmployee = checkStatement.executeQuery(findFirstEmployeeSql)) {

            if (firstEmployee.next()) {
                int firstId = firstEmployee.getInt("ID");
                String currentName = firstEmployee.getString("NAME");

                // Если имя начинается с большой буквы - делаем его с маленькой для теста
                if (Character.isUpperCase(currentName.charAt(0))) {
                    String testName = currentName.substring(0, 1).toLowerCase() + currentName.substring(1);

                    // Обновляем имя первого сотрудника на тестовое
                    try (PreparedStatement testUpdate = connection.prepareStatement(updateNameSql)) {
                        testUpdate.setString(1, testName);
                        testUpdate.setInt(2, firstId);
                        testUpdate.executeUpdate();
                        System.out.println("Создана тестовая запись с именем: " + testName);
                    }
                }
            }
        }

        // Исправляем все имена
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findNamesSql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                // Проверяем, начинается ли имя с маленькой буквы
                if (name.length() > 0 && Character.isLowerCase(name.charAt(0))) {
                    String correctedName = name.substring(0, 1).toUpperCase() + name.substring(1);

                    try (PreparedStatement updateStatement = connection.prepareStatement(updateNameSql)) {
                        updateStatement.setString(1, correctedName);
                        updateStatement.setInt(2, id);
                        updateStatement.executeUpdate();
                        correctedCount++;
                    }
                }
            }

            System.out.println("Исправлено имен: " + correctedCount);
        }
    }

    public static void countITEmployees(Connection connection) {
        String sql = "SELECT COUNT(*) as employeeCount " +
                "FROM Employee " +
                "WHERE DEPARTMENTID = (SELECT ID FROM Department WHERE NAME = 'IT')";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                int count = resultSet.getInt("employeeCount");
                System.out.println("Количество сотрудников в IT-отделе: " + count);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при подсчете сотрудников: " + e.getMessage());
        }
    }
}