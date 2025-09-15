package com.stepup.app;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Scanner;

public class Office {
    public static void main(String[] args) {
        // Создаем базу данных с подключением
        IDatabaseService dbService = new DatabaseServiceImpl("jdbc:h2:.\\Office");

        // Создаем сервисный слой
        Service service = new Service(dbService);

        // Создаем UI-менеджер
        UIManager uiManager = new UIManager(service);

        // Запускаем приложение
        uiManager.run();
    }
}
