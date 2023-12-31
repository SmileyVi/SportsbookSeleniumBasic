package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    @FindBy(css = "a[data-test-id='Live In-Game-link']")
    WebElement linkLiveInGame;

    @FindBy(css = "button[id='truste-consent-button']")
    WebElement acceptAllCookiesButton;

    public HomePage() {
        PageFactory.initElements(driver, this);
    }
    public void navigateToLiveInGamePage() {
        logger.info("Navigating to Live In-Game page...");
        linkLiveInGame.click();
    }

    public void acceptAllCookies() {
        logger.info("Accepting all cookies...");
        TestUtils.waitForElementVisible(acceptAllCookiesButton);
        acceptAllCookiesButton.click();
    }
}
