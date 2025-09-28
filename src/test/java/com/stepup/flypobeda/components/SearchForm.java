package com.stepup.flypobeda.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchForm {
    private WebDriver driver;

    public SearchForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
/*
    // закрываем popup с политикой, что бы не мешал выполнять действия
        driver.findElement(By.cssSelector("[data-testid=\"policy-popup-close-icon\"]")).click();

    //Заполняем значение откуда Санкт-Петербург
        driver.findElement(By.cssSelector("[placeholder=\"Откуда\"]")).click();
        driver.findElement(By.cssSelector("[placeholder=\"Откуда\"]")).sendKeys("Санкт-Петербург");
        driver.findElement(By.cssSelector("[role=\"menuitem\"]")).click();

    //Заполняем значение куда Москва
        driver.findElement(By.cssSelector("[placeholder=\"Куда\"]")).click();
        driver.findElement(By.cssSelector("[placeholder=\"Куда\"]")).sendKeys("Москва");
        driver.findElement(By.cssSelector("[role=\"menuitem\"]")).click();

    // Выбираем дату в один конец
        driver.findElement(By.cssSelector("[placeholder=\"Туда\"]")).click();
        driver.findElement(By.xpath("//button[text()=\"30\"]")).click();
        driver.findElement(By.xpath("//button[text()=\"Обратный билет не нужен\"]")).click();
        driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
*/

    @FindBy(css = "form")
    private WebElement form;

    @FindBy(css = "form div:nth-child(1) input")
    private WebElement fromField;

    @FindBy(css = "form div:nth-child(4) input")
    private WebElement toField;

    //driver.findElement(By.cssSelector("[role='menuitem']")).click();
    @FindBy(css = "[role='menuitem']")
    private WebElement menuitem;

    @FindBy(css = "form > div > div > div > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) input")
    private WebElement fromDate;

    @FindBy(css = "form > div > div > div > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) input")
    private WebElement toDate;

    @FindBy(css = "form > div > div > div > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) [data-failed='true']")
    private WebElement redBorder;

    @FindBy(css = "main > div > div:nth-child(4) > div > div > div > button > span")
    private WebElement middleElement;

    //форма поиска, переключение в режим поиска бронирования
    @FindBy(css = "[class*='tabsControl'] button:nth-child(3)")
    private WebElement tab3BookingSearch;

    @FindBy(css = "form > div > div:nth-child(1) input")
    private WebElement input1Familia;

    @FindBy(css = "form > div > div:nth-child(2) input")
    private WebElement input2BookingNumber;

    @FindBy(css = "[class*='submit']")
    private WebElement submitButton;

    //checkbox
    @FindBy(className = "customCheckbox")
    private WebElement checkbox;

    //.message_error
    @FindBy(className = "message_error")
    private WebElement errorMessage;


    public void setFrom(String city) {
        scrollToElement(middleElement);

        fromField.clear();
        fromField.sendKeys(city);
        waitForElementToBeVisible(menuitem);
        menuitem.click();
    }

    public void setTo(String city) {
        scrollToElement(middleElement);
        toField.clear();
        toField.sendKeys(city);
        waitForElementToBeVisible(menuitem);
        menuitem.click();
    }

    public void setFromDate(){
        driver.findElement(By.cssSelector("[placeholder=\"Туда\"]")).click();
        driver.findElement(By.xpath("//button[text()=\"30\"]")).click();
        driver.findElement(By.xpath("//button[text()=\"Обратный билет не нужен\"]")).click();
        driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
    }

    public void clickSearch() {
        form.submit();
    }

    public String getColorFromDateHighlighter(){
        // Преобразование в HEX
        Color color = Color.fromString(redBorder.getCssValue("border-color"));
        return  color.asHex().toUpperCase();
    }

    public void waitForColorChange(String expectedColor) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> {
                    String actualColor = getColorFromDateHighlighter();
                    return actualColor.equals(expectedColor);
                });
    }

    public void waitForElementToBeVisible(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
    }


    public void scrollToElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void switchToBookingSearchMode() {
        tab3BookingSearch.click();
    }

    public void fillSearchForm(String orderNumber, String surname) {

        input1Familia.clear();
        input1Familia.sendKeys(surname);
        input2BookingNumber.clear();
        input2BookingNumber.sendKeys(orderNumber);
    }

    public String getFamiliaInputText(){
        return input1Familia.getAttribute("placeholder");
    }

    public String getBookingNumberText(){
        return input2BookingNumber.getAttribute("placeholder");
    }

    public String getSubmitButtonText(){
        return submitButton.getText();
    }

    public void setCheckBox(){
        checkbox.click();
    }

    public String getErrorMessage(){
       return errorMessage.getText();
    }
}
