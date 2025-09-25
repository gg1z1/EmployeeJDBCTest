package com.stepup.flypobeda.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Header {
    private WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Добавляем эту строку
    }

    //поиск лого
    @FindBy(css = "img[src*='logo']")
    private WebElement logo;

    //наведение на меню информации
    @FindBy(css = "header [href='/information']")
    private WebElement infoMenu;

    //внутренний элемент меню информации "Подготовка к полёту" элемент collapse меню Информация
    @FindBy(css = "header [href='/information#flight']")
    private WebElement prepareForFlight;

    //внутренний элемент меню информации "Полезная информация" элемент collapse меню Информация
    @FindBy(css = "header [href='/information#useful']")
    private WebElement usefulInfo;

    //внутренний элемент меню информации "О компании" элемент collapse меню Информация
    @FindBy(css = "header [href='/information#company']")
    private WebElement aboutCompany;

    @FindBy(css = "header button:nth-child(1)")
    private WebElement languageSelector;

    //$("header button:nth-child(1)")
    //$("header button:nth-child(2)")
    //$("header [href*='Login']")

    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

    public void hoverToInfoMenu() {
        Actions actions = new Actions(driver);
        actions.moveToElement(infoMenu).perform();
    }


    public WebElement getPrepareForFlight() {
        hoverToInfoMenu();
        return prepareForFlight;
    }

    public WebElement getUsefulInfo() {
        hoverToInfoMenu();
        return usefulInfo;
    }

    public WebElement getAboutCompany() {
        hoverToInfoMenu();
        return aboutCompany;
    }

    public String getLanguage() {

        String language = languageSelector.getText();
        //String language = "РУС";
        if(language.toUpperCase().equals("РУС")) return "RU";
        if(language.toUpperCase().equals("ENG")) return "EN";
        return "RU";
    }

    public String getLogoAttr(String src) {
        return logo.getAttribute(src);
    }
}
