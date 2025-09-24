package com.stepup.flypobeda;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UITestWait {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        // Инициализация драйвера
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();

        // Настройка окна браузера
        driver.manage().window().maximize();

        // Настройки Implicit Wait
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        // Настройка EXPLICIT WAIT
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }


    @DisplayName("Проверка работы с явными ожиданиями")
    @Test
    public void openUrlTest() {
        /*
        // Гугл требует проверку капчи, по этому опустим данные шаги
        // Шаг 1: Поиск сайта Победы

        driver.get("https://www.google.com");

        // Находим поле поиска и вводим запрос
        WebElement searchField = driver.findElement(By.cssSelector("[title=\"Поиск\"]"));
        searchField.sendKeys("Сайт компании Победа");
        searchField.submit();

        //добавил что бы успеть ввести капчу вручную
        try {
            Thread.sleep(20000); // Пауза на 2 секунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Шаг 2: Переход на сайт Победы
        // Ждем появления результатов поиска
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.partialLinkText("Авиакомпания «Победа»")
        ));

        // Кликаем по ссылке
        WebElement firstResult = driver.findElement(By.partialLinkText("Авиакомпания «Победа»"));
        firstResult.click();
        */

        //Шаг: меняем задание - т.к. по заданию должен быть банер "Калининград"
        //этого банер отсутствует
        //открываем сайт Победы
        driver.get("https://www.flypobeda.ru/");

        // закрываем popup с политикой, что бы не мешал выполнять действия
        driver.findElement(By.cssSelector("[data-testid=\"policy-popup-close-icon\"]")).click();

        //Заполняем значение откуда Санкт-Петербург
        driver.findElement(By.cssSelector("[placeholder=\"Откуда\"]")).click();
        driver.findElement(By.cssSelector("[placeholder=\"Откуда\"]")).sendKeys("Санкт-Петербург");
        driver.findElement(By.cssSelector("[role=\"menuitem\"]")).click();

        //Заполняем значение куда Москва
        driver.findElement(By.cssSelector("[placeholder=\"Куда\"]")).click();
        driver.findElement(By.cssSelector("[placeholder=\"Куда\"]")).sendKeys("Москва");
        driver.findElement(By.cssSelector("[role=\"menuitem\"]")).click();

        // Выбираем дату в один конец
        driver.findElement(By.cssSelector("[placeholder=\"Туда\"]")).click();
        driver.findElement(By.xpath("//button[text()=\"30\"]")).click();
        driver.findElement(By.xpath("//button[text()=\"Обратный билет не нужен\"]")).click();
        driver.findElement(By.cssSelector("[type=\"submit\"]")).click();

        // Шаг 3: Проверка баннера на сайте Победы
        // Банера нет - открывается окно с отелями
        // Довольно долго грузятся список отелей секунд 5,
        // установили лимит в 15 сек явного ожидания появления первой кнопки
        // "Показать все отели".

        WebElement bannerText = wait.until(driver -> {
            WebElement element = driver.findElement(By.cssSelector("[class*=\"HotelCard_ctaButton\"]"));
            return element.isDisplayed() ? element : null;
        });

        // Проверяем текст на кнопке
        assertEquals("Показать все номера", bannerText.getText());

        // Шаг 4: Смена языка и проверка текстов
        // находим и кликаем переключатель языка
        driver.findElement(By.cssSelector("[class*=\"Widgets_widgets\"] > div:nth-child(1) button")).click();
        driver.findElement(By.cssSelector("[class*=\"LanguageWidget_list\"] div:nth-child(1)")).click();

        // Ждем перезагрузки сраницы , и подгрузки списка отелей
        bannerText = wait.until(driver -> {
            WebElement element = driver.findElement(By.cssSelector("[class*=\"HotelCard_ctaButton\"]"));
            return element.isDisplayed() ? element : null;
        });

        //проверяем английский текст
        assertEquals("Show all rooms", bannerText.getText());
    }
}
