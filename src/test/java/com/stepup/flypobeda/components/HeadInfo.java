package com.stepup.flypobeda.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeadInfo {
    private WebDriver driver;

    // Локаторы элементов
    @FindBy(css = "title")
    private WebElement pageTitle;

    public String getCurrentTitle() {
        return driver.getTitle();
    }

    public HeadInfo(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
