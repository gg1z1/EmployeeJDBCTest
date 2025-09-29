package com.stepup.flypobedaselenid.pages;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.visible;

public class BasePage {
    // Базовый URL приложения
    protected static final String BASE_URL = "https://pobeda.aero";

    // Конструктор базового класса
    public BasePage() {
        // Настройка конфигурации Selenide
        Configuration.browserSize = "1366x678";
        Configuration.holdBrowserOpen = false;

        // Открытие стартовой страницы
        openPage(BASE_URL);

        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Проверка отображения основных элементов
        //$(By.cssSelector(".header-logo")).shouldBe(visible);
        //$(By.cssSelector("title")).shouldHave(text("Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы"));
    }

    // Метод для открытия страницы
    protected void openPage(String url) {
        Selenide.open(url);
    }

    // Метод для прокрутки страницы
    protected void scrollToElement(SelenideElement element) {
        element.scrollTo();
    }

    // Метод для ожидания появления элемента
    protected void waitForElement(SelenideElement element) {
        element.shouldBe(visible);
    }
}
