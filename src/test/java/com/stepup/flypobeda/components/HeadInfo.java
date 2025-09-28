package com.stepup.flypobeda.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public void waitForPageTitle(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Увеличиваем время ожидания

        // Ожидание точного совпадения заголовка
        wait.until(ExpectedConditions.titleIs(expectedTitle));
    }
}
