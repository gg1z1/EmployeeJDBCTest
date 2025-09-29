package com.stepup.flypobedaselenid.components;

import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class HeaderPage {
    // Элементы заголовка
    private final SelenideElement logo = $(By.cssSelector("img[src*='logo']"));
    private final SelenideElement infoMenu = $(By.cssSelector("header [href='/information']"));
    private final SelenideElement prepareForFlight = $(By.cssSelector("header [href='/information#flight']"));
    private final SelenideElement usefulInfo = $(By.cssSelector("header [href='/information#useful']"));
    private final SelenideElement aboutCompany = $(By.cssSelector("header [href='/information#company']"));
    private final SelenideElement languageSelector = $(By.cssSelector("header button:nth-child(1)"));

    // Проверка отображения логотипа
    public boolean isLogoDisplayed() {
        return logo.is(visible);
    }

    // Наведение на меню информации
    public void hoverToInfoMenu() {
        infoMenu.hover();
    }

    // Получение текста элемента "Подготовка к полёту"
    public String getPrepareForFlightText() {
        hoverToInfoMenu();
        return prepareForFlight.shouldBe(visible).getText();
    }

    // Получение текста элемента "Полезная информация"
    public String getUsefulInfoText() {
        hoverToInfoMenu();
        return usefulInfo.shouldBe(visible).getText();
    }

    // Получение текста элемента "О компании"
    public String getAboutCompanyText() {
        hoverToInfoMenu();
        return aboutCompany.shouldBe(visible).getText();
    }

    // Получение текущего языка
    public String getLanguage() {
        String language = languageSelector.getText();
        if (language.toUpperCase().equals("РУС")) return "RU";
        if (language.toUpperCase().equals("ENG")) return "EN";
        return "RU";
    }

    // Получение атрибута src логотипа
    public String getLogoSrc() {
        return logo.getAttribute("src");
    }
}