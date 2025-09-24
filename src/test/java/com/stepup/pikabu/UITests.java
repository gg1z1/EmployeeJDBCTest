package com.stepup.pikabu;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class UITests {

    private static WebDriver driver;
    private static String baseUrl = "https://pikabu.ru/";
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        // Инициализация драйвера
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Duration timeout = Duration.ofSeconds(5);
        wait = new WebDriverWait(driver, timeout);

        // Переход на сайт
        driver.get(baseUrl);
    }

    @AfterAll
    public static void tearDown() {
        // Закрытие браузера
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginFailure() {
        // Шаг 1: Проверка заголовка страницы
        String expectedTitle = "Горячее – самые интересные и обсуждаемые посты | Пикабу";
        assertEquals(expectedTitle, driver.getTitle());

        // Шаг 2: Нажать на кнопку "Войти"
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[class*=\"login-button\"]")));
        loginButton.click();

        // Шаг 3: Проверка элементов модального окна
        WebElement modal = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[class*=\"popup_auth\"]")));
        assertTrue(modal.isDisplayed());

        // Проверка заголовка модального окна //auth__header
        WebElement modalTitle = modal.findElement(By.cssSelector("[class=\"auth__header\"]"));
        assertEquals("Войти", modalTitle.getText());

        // Проверка полей ввода
        WebElement loginField = modal.findElement(By.name("username"));
        assertTrue(loginField.isDisplayed());

        WebElement passwordField = modal.findElement(By.name("password"));
        assertTrue(passwordField.isDisplayed());

        // Проверка кнопки входа
        WebElement submitButton = modal.findElement(By.cssSelector("[type=\"submit\"]"));
        assertTrue(submitButton.isDisplayed());

        // Шаг 4: Ввод некорректных данных
        loginField.sendKeys("Qwerty");
        passwordField.sendKeys("Qwerty");
        submitButton.click();

        try {
            Thread.sleep(2000); // Пауза на 2 секунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Шаг 5: Проверка сообщения об ошибке //auth__error
        String expectedError = "Ошибка. Вы ввели неверные данные авторизации";
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.popup__content [class*=\"auth__error\"]")));
        assertEquals(expectedError, errorMessage.getText());
    }
}
