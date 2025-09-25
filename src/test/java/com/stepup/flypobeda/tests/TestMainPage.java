package com.stepup.flypobeda.tests;

import com.stepup.flypobeda.pages.MainPage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
