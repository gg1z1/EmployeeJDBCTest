package com.stepup.flypobeda.tests;

import com.stepup.flypobeda.pages.MainPage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMainPage {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.flypobeda.ru/");
        mainPage = new MainPage(driver);
        mainPage.closeAdsAndPolicy();
    }

    @Test
    public void testMainPage() {
        // Шаг 1: Проверка заголовка и логотипа
        assertEquals(mainPage.getExpectedTitle(mainPage.getHeader().getLanguage())
                     ,mainPage.getHeadInfo().getCurrentTitle()
                     ,"Title не совпадают");

        // Шаг 2: Проверка логотипа
        assertTrue(mainPage.getHeader().getLogoAttr("src").matches(".*\\.svg")
                    , "Строка не соответствует формату SVG");
        assertTrue(mainPage.getHeader().isLogoDisplayed());

        // Шаг 3: Наведение на меню и Проверка всплывающего меню
        assertEquals("Подготовка к полёту"
                ,mainPage.getHeader().getPrepareForFlight().getText());
        assertEquals("Полезная информация"
                , mainPage.getHeader().getUsefulInfo().getText());
        assertEquals("О компании"
                , mainPage.getHeader().getAboutCompany().getText());

    }

    @Test
    public void negativeTestSearchForm (){
        //action
        mainPage.getSearchForm().setFrom("Санкт-Петербург");
        mainPage.getSearchForm().setTo("Москва");
        mainPage.getSearchForm().clickSearch();
        mainPage.getSearchForm().waitForColorChange("#D50062");

        assertEquals("#D50062",
                    mainPage.getSearchForm().getColorFromDateHighlighter());
    }

    @Test
    public void negativeTestBookingSearchForm (){
        //action
        mainPage.getSearchForm().switchToBookingSearchMode();

        assertEquals("Фамилия клиента"
                ,mainPage.getSearchForm().getFamiliaInputText());
        assertEquals("Номер бронирования или билета"
                ,mainPage.getSearchForm().getBookingNumberText());
        assertEquals("ПОИСК"
                ,mainPage.getSearchForm().getSubmitButtonText());

        mainPage.getSearchForm().fillSearchForm("XXXXXX", "Qwerty");
        mainPage.getSearchForm().clickSearch();

        // Сохраняем handle основной вкладки
        String mainWindow = driver.getWindowHandle();

        // Ждем появления новой вкладки
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));

        // Перебираем все вкладки и находим нужную по URL
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getCurrentUrl().contains("ticket.flypobeda.ru/websky")) {
                break;
            }
        }

        assertTrue(driver.getCurrentUrl().contains("ticket.flypobeda.ru/websky"));

        mainPage.getHeadInfo().waitForPageTitle("Просмотр заказа");

        assertEquals("Просмотр заказа"
                ,mainPage.getHeadInfo().getCurrentTitle()
                ,"Title не совпадают");

        mainPage.getSearchForm().setCheckBox();
        mainPage.getSearchForm().clickSearch();

        assertEquals("Заказ с указанными параметрами не найден",
                mainPage.getSearchForm().getErrorMessage());

//        try {
//            Thread.sleep(20000); // Пауза на 2 секунды
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
