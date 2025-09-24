package com.stepup.flypobeda;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMainPage {
    private static WebDriver driver;
    private static MainPage mainPage;

    @BeforeAll
    public static void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.flypobeda.ru/");
        mainPage = new MainPage(driver);
    }

    @Test
    public void testMainPage() {
        // Шаг 1: Проверка заголовка и логотипа
        assertTrue(mainPage.checkPageTitle());
        assertTrue(mainPage.isLogoPresent());

        // Шаг 2: Наведение на меню
        mainPage.hoverToInfoMenu();

        // Шаг 3: Проверка всплывающего меню
        assertTrue(mainPage.checkInfoDropdown());
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
