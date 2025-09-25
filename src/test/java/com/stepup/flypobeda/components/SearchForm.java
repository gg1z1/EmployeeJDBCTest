package com.stepup.flypobeda.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    //main > div > div:nth-child(4) > div > div > div > button > span


    public void setFrom(String city) {
        fromField.sendKeys(city);
        scrollToElement(middleElement);
        menuitem.click();
    }

    public void setTo(String city) {
        toField.sendKeys(city);
        scrollToElement(middleElement);
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

    public void scrollToElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
}
