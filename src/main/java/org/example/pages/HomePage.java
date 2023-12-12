package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    @FindBy(css = "a[data-test-id='Live In-Game-link']")
    WebElement linkLiveInGame;

    @FindBy(css = "button[id='truste-consent-button']")
    WebElement acceptiAllCookiesButton;

    @FindBy(css = "#title=Sports Betting | Bet Online Legally with DraftKings Sportsbook")
    WebElement title;

    public HomePage() {
        PageFactory.initElements(driver, this);
    }
    public void navigateToLiveInGamePage() {
        logger.info("Navigating to Live In-Game page...");
        linkLiveInGame.click();
    }

    public void acceptAllCookies() {
        logger.info("Accepting all cookies...");
        getAcceptAllCookiesButton().click();
    }

    private WebElement getAcceptAllCookiesButton() {
        //waitForElementVisible(acceptiAllCookiesButton, testConfig.getTimeout());
        return acceptiAllCookiesButton;
    }

}

