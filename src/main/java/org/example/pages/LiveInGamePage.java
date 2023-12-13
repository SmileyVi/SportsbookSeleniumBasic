package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class LiveInGamePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LiveInGamePage.class);
    private static final String LOGIN_TO_PLACE_BETS_BUTTON_TEXT = "Log In to Place Bets";
    private String betName = "";

    private Random random = new Random();

    public LiveInGamePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "a[data-testid='sportsbook-tabbed-subheader-tab-link']")
    List<WebElement> sportCategories;

    @FindBy(css = "div[aria-label='Game Subcategory Selector'] > span")
    WebElement betTypeSelectionDropdown;

    @FindBy(css = "ul[aria-label='Subcategory options list'] > li")
    List<WebElement> betTypeOptions;

    @FindBy(xpath = "//div[contains(@class,'sportsbook-outcome-cell')]/div[@role='button']")
    List<WebElement> bettingSelectionButtons;

    @FindBy(css = "div[class='sportsbook-header']")
    List<WebElement> eventHeaders;

    @FindBy(css = "div[class='sportsbook-event-accordion__wrapper expanded'] > div[role='button'] > a")
    List<WebElement> sportEventButtons;

    @FindBy(css = "div[class='event-cell__name']")
    List<WebElement> sportEventCells;

    @FindBy(css = "span[class='single-card-header__text-top-outcome-label']")
    WebElement betSlipForBetLabel;

    @FindBy(css = "div[class='dk-place-bet-button__primary-text']")
    WebElement loginToPlaceBetButton;

    public List<WebElement> getSportCategoriesTabs() {
        TestUtils.waitForElementsVisible(sportCategories);
        return sportCategories;
    }

    public boolean atLeastOneSportEventIsDisplayed() {
        return eventHeaders.size() > 0;
    }

    public WebElement getBetTypeSelectionDropdown() {
        return betTypeSelectionDropdown;
    }

    public List<WebElement> getBetTypeOptions() {
        return betTypeOptions;
    }

    public List<WebElement> getBettingSelectionButtons() {
        return bettingSelectionButtons;
    }

    public WebElement getBetSlipForBetLabel() {
        return betSlipForBetLabel;
    }

    public boolean betSlipIsAdded() {
        return getBetSlipForBetLabel().getText().equals(betName);
    }

    public WebElement getRandomBettingButton() {
        TestUtils.explicitWait();
        return TestUtils.getRandomElementFromList(getBettingSelectionButtons());
    }

    private List<WebElement> getBetTypeOptionsFromDropdown() {
        WebElement betTypeDropdown = getBetTypeSelectionDropdown();

        try {
            hoverOverElement(betTypeDropdown);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return getBetTypeOptions();
    }

    public void changeBetType() {
        List<WebElement> betOptions = getBetTypeOptionsFromDropdown();
        if (betOptions.size() > 0) {
            betOptions.get(0).click();
        }
    }

    public String placeBetOnRandomButton() {
        WebElement betButton = getRandomBettingButton();
        betButton.click();
        return betButton.getText();
    }

    private WebElement getLoginToPlaceBetButton() {
        TestUtils.waitForElementVisible(loginToPlaceBetButton);
        return loginToPlaceBetButton;
    }

    public boolean loginToPlaceBetsButtonIsDisplayed() {
        return getLoginToPlaceBetButton().isDisplayed()
                && getLoginToPlaceBetButton().getText().equals(LOGIN_TO_PLACE_BETS_BUTTON_TEXT);
    }

    public String selectRandomSport() {
        List<WebElement> availableSports = getSportCategoriesTabs();
        WebElement sport = TestUtils.getRandomElementFromList(availableSports);
        sport.click();
        logger.info("Selecting sport tab " + sport.getText());
        return sport.getText();
    }

    public void hoverOverElement(WebElement elementToHover) throws InterruptedException {
        new Actions(driver).moveToElement(elementToHover).build().perform();
        TestUtils.explicitWait();
    }

    public EventPage clickOnSportEvent(WebElement sportEventToClick) {
        TestUtils.scrollToElement(sportEventToClick);
        sportEventToClick.click();
        TestUtils.waitForElementVisible(sportsbookLogo);
        return new EventPage();
    }

    public WebElement getRandomSportEvent() {
        if (sportEventButtons.size() > 0) {
            return TestUtils.getRandomElementFromList(sportEventButtons);
        } else {
            return TestUtils.getRandomElementFromList(sportEventCells);
        }
    }

    public boolean userIsOnLiveInGamesUrl() {
        return driver.getCurrentUrl().equals(TestUtils.baseUrl + TestUtils.liveInGameUrl);
    }
}
