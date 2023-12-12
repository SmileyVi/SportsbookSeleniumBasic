package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.TestBase;
import org.example.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class BasePage extends TestBase {
    public BasePage() {
        PageFactory.initElements(driver, this);
    }
    private final Logger logger = LogManager.getLogger(BasePage.class);

    @FindBy(css = "div[aria-label='DraftKings Sportsbook Logo']")
    WebElement sportsbookLogo;

    @FindBy(css="nav .sportsbook-breadcrumb__end >h1")
    WebElement sectionHeader;

    public void open(String url) {
        driver.get(url);
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isSportsbookLogoDisplayed() {
        TestUtils.waitForElementVisible(sportsbookLogo);
        return sportsbookLogo.isDisplayed();
    }

    public String getSectionHeader() {
        TestUtils.waitForElementVisible(sectionHeader);
        return sectionHeader.getText();
    }

    public boolean isUrlContainsExpectedParams(String expectedPage, String additionalParam) {
        String currentUrl = getCurrentUrl();
        return currentUrl.toLowerCase().contains(expectedPage) && currentUrl.toLowerCase().contains(additionalParam.toLowerCase());
    }
}
