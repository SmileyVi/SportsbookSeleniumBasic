package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private static final String ID_ACCEPT_ALL_COOKIES_BUTTON_LOCATOR = "truste-consent-button";

    @FindBy(xpath = "//a[text()='Live In-Game']")
    WebElement linkLiveInGame;


    public HomePage() {
        PageFactory.initElements(driver, this);
    }
    public void navigateToLiveInGamePage() {
        logger.info("Navigating to Live In-Game page...");
        linkLiveInGame.click();
    }

    public void acceptAllCookies() {
        logger.info("Accepting all cookies...");
        WebElement acceptAllCookiesButton = TestUtils.getElementById(ID_ACCEPT_ALL_COOKIES_BUTTON_LOCATOR);
        acceptAllCookiesButton.click();
    }
}
