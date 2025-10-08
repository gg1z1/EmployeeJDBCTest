package com.stepup.flypobedaselenid.tests;

import com.codeborne.selenide.Selenide;
import com.stepup.flypobedaselenid.components.HeaderPage;
import com.stepup.flypobedaselenid.components.SearchFormPage;
import com.stepup.flypobedaselenid.pages.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Epic("Функциональное тестирование главной страницы")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestMainPage {

    private MainPage mainPage;
    private HeaderPage headerPage;
    private SearchFormPage searchFormPage;

    @BeforeEach
    public void setUp() {
        mainPage = new MainPage();
        headerPage = new HeaderPage();
        searchFormPage = new SearchFormPage();
    }

    @Test
    @Feature("Проверка элементов главной страницы")
    @DisplayName("Проверка заголовка, логотипа и всплывающего меню")
    public void testMainPage() {
        // Проверка заголовка страницы
        String expectedTitle = mainPage.getExpectedTitle(headerPage.getLanguage());
        assertEquals(expectedTitle, Selenide.title(), "Заголовок страницы не совпадает");

        // Проверка логотипа
        headerPage.isLogoDisplayed();
        assertTrue(headerPage.getLogoSrc().contains(".svg"), "Логотип не найден или имеет неверный формат");

        // Проверка всплывающего меню
        headerPage.hoverToInfoMenu();
        assertEquals("Подготовка к полёту", headerPage.getPrepareForFlightText());
        assertEquals("Полезная информация", headerPage.getUsefulInfoText());
        assertEquals("О компании", headerPage.getAboutCompanyText());
    }

    @Test
    @Feature("Негативное тестирование формы поиска")
    @DisplayName("Проверка валидации формы поиска при некорректных данных")
    public void negativeTestSearchForm() {
        // Заполняем форму поиска
        searchFormPage.setFrom("Санкт-Петербург");
        searchFormPage.setTo("Москва");
        searchFormPage.clickSearch();
        searchFormPage.waitForColorChange("rgb(213, 0, 98)");
        // Проверяем красную рамку
        assertEquals("rgb(213, 0, 98)", searchFormPage.getColorFromDateHighlighter(), "Цвет рамки не соответствует ожидаемому");
    }

    @Test
    @Feature("Проверка поиска бронирования")
    @DisplayName("Тестирование работы формы поиска бронирования")
    public void negativeTestBookingSearch() {
        // Переключаемся в режим поиска бронирования
        searchFormPage.switchToBookingMode();

        // Проверяем тексты полей
        assertEquals("Фамилия клиента", searchFormPage.getFamiliaInputText());
        assertEquals("Номер бронирования или билета", searchFormPage.getBookingNumberText());
        assertEquals("ПОИСК", searchFormPage.getSubmitButtonText());

        // Заполняем форму
        searchFormPage.fillBookingForm("Qwerty", "XXXXXX");
        searchFormPage.clickSearch();

        // Ждем появления новой вкладки
        Selenide.switchTo().window(1);

        // Ждем загрузки нужной страницы
        $(By.cssSelector("body")).shouldBe(visible);

        // Проверяем URL
        webdriver()
                .shouldHave(urlContaining("ticket.flypobeda.ru/websky"));

        // Проверяем заголовок страницы
        Selenide.title().contains("Просмотр заказа");

        // Проверяем ошибку
        searchFormPage.setCheckbox();
        searchFormPage.clickSearch();
        String errorText = searchFormPage.getErrorMessage();
        assertEquals("Заказ с указанными параметрами не найден", errorText);
    }

}
