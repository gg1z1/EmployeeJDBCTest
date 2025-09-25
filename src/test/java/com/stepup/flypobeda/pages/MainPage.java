package com.stepup.flypobeda.pages;

import com.stepup.flypobeda.components.HeadInfo;
import com.stepup.flypobeda.components.SearchForm;
import com.stepup.flypobeda.components.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class MainPage extends BasePage {

    private static final Map<String, String> EXPECTED_TITLES = new  HashMap<>();

    static {
        EXPECTED_TITLES.put("RU", "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками");
        EXPECTED_TITLES.put("EN", "Pobeda Airlines - buy cheap plane tickets online, direct and transfer flights");
    }

    private HeadInfo headInfo;
    private SearchForm searchForm;
    private Header header;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.headInfo = new HeadInfo(driver);
        this.header = new Header(driver);
        this.searchForm = new SearchForm(driver);
    }

    public HeadInfo getHeadInfo() {
        return headInfo;
    }

    public Header getHeader() {
        return header;
    }


    public SearchForm getSearchForm() {
        return searchForm;
    }

    public String getExpectedTitle(String languageTitle){
        return EXPECTED_TITLES.get(languageTitle);
    }

}
