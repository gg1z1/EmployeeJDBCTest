package com.stepup.flypobeda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {
    // Локаторы элементов
    @FindBy(css = "title")
    private WebElement pageTitle;

    @FindBy(css = "img[src*=\"logo\"")
    private WebElement logo;

    @FindBy(css = "header [href=\"/information\"]")
    private WebElement infoMenu;

    @FindBy(css = "header [href=\"/information#flight\"]")
    private WebElement prepareForFlight;

    @FindBy(css = "header [href=\"/information#useful\"]")
    private WebElement usefulInfo;

    @FindBy(css = "header [href=\"/information#company\"]")
    private WebElement aboutCompany;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Проверка заголовка страницы
    public boolean checkPageTitle() {
        //waitForElement(pageTitle);
        return driver.getTitle().equals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками");
    }

    // Проверка наличия логотипа
    public boolean isLogoPresent() {
        waitForElement(logo);
        return logo.isDisplayed();
    }

    // Наведение на пункт меню "Информация"
    public void hoverToInfoMenu() {
        Actions actions = new Actions(driver);
        actions.moveToElement(infoMenu).perform();
    }

    // Проверка всплывающего меню
    public boolean checkInfoDropdown() {
        waitForElement(prepareForFlight);
        waitForElement(usefulInfo);
        waitForElement(aboutCompany);

        return prepareForFlight.isDisplayed()
                && usefulInfo.isDisplayed()
                && aboutCompany.isDisplayed();
    }
}
