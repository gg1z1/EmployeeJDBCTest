package com.stepup.flypobedaselenid.pages;

import com.codeborne.selenide.SelenideElement;
import com.stepup.flypobedaselenid.components.HeaderPage;
import com.stepup.flypobedaselenid.components.SearchFormPage;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends  BasePage{
    // Ссылки на страницы компонентов
    private HeaderPage headerPage = new HeaderPage();
    private SearchFormPage searchFormPage = new SearchFormPage();

    // Элементы для закрытия попапов
    private final SelenideElement adsCloseButton = $(By.cssSelector("[data-testid='ads-popup-close-icon']"));
    private final SelenideElement policyCloseButton = $(By.cssSelector("[data-testid='policy-popup-close-icon']"));

    // Ожидаемые заголовки страниц
    private static final Map<String, String> EXPECTED_TITLES = new HashMap<>() {{
        put("RU", "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками");
        put("EN", "Pobeda Airlines - buy cheap plane tickets online, direct and transfer flights");
    }};

    // Метод для получения заголовка страницы
    public String getExpectedTitle(String language) {
        return EXPECTED_TITLES.get(language);
    }

    // Метод для закрытия рекламных и политических попапов
    public void closePopups() {
        adsCloseButton.shouldBe(visible).click();
        policyCloseButton.shouldBe(visible).click();
    }

    // Геттеры для доступа к компонентам
    public HeaderPage getHeaderPage() {
        return headerPage;
    }

    public SearchFormPage getSearchFormPage() {
        return null;
    }

    public void closeAdsAndPolicy() {
    }
}
