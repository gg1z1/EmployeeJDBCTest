package com.stepup.flypobedaselenid.components;

import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class SearchFormPage {
    // Элементы формы поиска
    private final SelenideElement form = $(By.cssSelector("form"));
    private final SelenideElement fromField = $(By.cssSelector("[placeholder='Откуда']"));
    private final SelenideElement toField = $(By.cssSelector("[placeholder='Куда']"));
    private final SelenideElement menuItem = $(By.cssSelector("[role='menuitem']"));
    private final SelenideElement searchButton = $(By.cssSelector("[type='submit']"));
    private final SelenideElement fromDate = $(By.cssSelector("[placeholder='Туда']"));

    // Элементы для поиска бронирования
    private final SelenideElement tabBooking = $(By.cssSelector("[class*='tabsControl'] button:nth-child(3)"));
    private final SelenideElement inputFamilia = $(By.cssSelector("form > div > div:nth-child(1) input"));
    private final SelenideElement inputBookingNumber = $(By.cssSelector("form > div > div:nth-child(2) input"));
    private final SelenideElement submitButton = $(By.cssSelector("[class*='submit']"));
    private final SelenideElement checkbox = $(By.cssSelector(".customCheckbox"));
    private final SelenideElement errorMessage = $(By.cssSelector(".message_error"));

    //
    private final SelenideElement redBorder = $(By.cssSelector(
            "form > div > div > div > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) [data-failed='true']"
    ));

    // Метод для установки города отправления
    public void setFrom(String city) {
        fromField.shouldBe(visible)
                .setValue(city)
                .pressEnter();
        menuItem.shouldBe(visible).click();
    }

    // Метод для установки города прибытия
    public void setTo(String city) {
        toField.shouldBe(visible)
                .setValue(city)
                .pressEnter();
        menuItem.shouldBe(visible).click();
    }

    // Выбор даты и отправка формы
    public void setDateAndSubmit() {
        fromDate.click();
        $(By.xpath("//button[text()='30']")).click();
        $(By.xpath("//button[text()='Обратный билет не нужен']")).click();
        searchButton.click();
    }

    // Переключение в режим поиска бронирования
    public void switchToBookingMode() {
        tabBooking.click();
    }

    // Заполнение формы поиска бронирования
    public void fillBookingForm(String surname, String number) {
        inputFamilia.setValue(surname);
        inputBookingNumber.setValue(number);
    }

    // Получение текста ошибки
    public String getErrorMessage() {
        return errorMessage.shouldBe(visible).getText();
    }

    // Установка галочки
    public void setCheckbox() {
        checkbox.click();
    }

    public void clickSearch() {
        form.submit();
    }

    public String getColorFromDateHighlighter() {
        return redBorder.getCssValue("border-color");
    }

    public void waitForColorChange(String expectedColor) {
        redBorder.shouldHave(cssValue("border-color", expectedColor));
    }

    // Метод для получения текста подсказки поля Фамилия
    public String getFamiliaInputText() {
        return inputFamilia.getAttribute("placeholder");
    }

    // Метод для получения текста подсказки поля Номер бронирования
    public String getBookingNumberText() {
        return inputBookingNumber.getAttribute("placeholder");
    }

    // Метод для получения текста кнопки поиска
    public String getSubmitButtonText() {
        return submitButton.getText();
    }
}
