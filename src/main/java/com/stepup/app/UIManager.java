package com.stepup.app;

import java.util.Scanner;

class UIManager {
    private final Service service;
    private final Scanner scanner;

    public UIManager(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        Option opt = Option.ADD_DEPARTMENT;

        while (!opt.equals(Option.EXIT)) {
            System.out.println("Выберите действие:");

            for (Option o : Option.values()) {
                System.out.println(o.getText());
            }

            try {
                int choice = scanner.nextInt();
                opt = Option.values()[choice];
                opt.action(service);
            } catch (Exception e) {
                System.out.println("Неверный ввод. Попробуйте еще раз.");
            }
        }
    }
}
