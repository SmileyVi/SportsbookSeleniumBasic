package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class LiveInGamePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LiveInGamePage.class);
    private static final String LOGIN_TO_PLACE_BETS_BUTTON_TEXT = "Log In to Place Bets";
    private static final String BET_BUTTONS_XPATH = "td/div/div[@role='button']";
    private static final String BET_NAMES_RELATED_XPATH = "./preceding-sibling::th//div[@class='event-cell__name-text']";
    private String betName = "";

    private Random random = new Random();

    public LiveInGamePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "a[data-testid='sportsbook-tabbed-subheader-tab-link']")
    List<WebElement> sportCategories;

    @FindBy(css="div[aria-label='Game Subcategory Selector'] > span")
    WebElement betTypeSelectionDropdown;

    @FindBy(css="ul[aria-label='Subcategory options list'] > li")
    List<WebElement> betTypeOptions;

    @FindBy(css="div[class='sportsbook-outcome-cell']")
    List<WebElement> bettingSelectionButtons;

    @FindBy(css="div[aria-label='Game Subcategory Selector'] > span[class='arrow']")
    WebElement betTypeDropdownArrow;

    @FindBy(css="div[class='sportsbook-header']")
    List<WebElement> eventHeaders;

    @FindBy(css="div[class='sportsbook-header__title'] > a")
    List<WebElement> sportEventLinks;

    @FindBy(css="div[class='sportsbook-accordion__children-wrapper']")
    WebElement betSportsbook;

    @FindBy(css="table[class='sportsbook-table']")
    WebElement sportsbookTable;

    @FindBy(css="table[class='sportsbook-table'] > tbody > tr")
    List<WebElement> sportsbookTableRows;

    @FindBy(css="span[class='single-card-header__text-top-outcome-label']")
    WebElement betSlipForBetLabel;

    @FindBy(css="div[class='dk-place-bet-button__primary-text']")
    WebElement loginToPlaceBetButton;

    public List<WebElement> getSportCategoriesTabs(){
        TestUtils.waitForElementsVisible(sportCategories);
        return sportCategories;
    }

    private List<WebElement> getSportsEventHeaders() {
        return eventHeaders;
    }

    public boolean atLeastOneSportEventIsDisplayed() {
        return eventHeaders.size() > 0;
    }

    public WebElement getBetTypeSelectionDropdown() {
        return betTypeSelectionDropdown;
    }

    public boolean areBetOptionsDisplayed() {
        if (betTypeOptions.size() == 0) {
            return false;
        }
        return betTypeOptions.get(0).isDisplayed();
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

    public WebElement findBettingButton() throws InterruptedException {
        List<WebElement> betOptions = getBetTypeOptionsFromDropdown();

        WebElement bettingButton = null;
        for (int i = 0; i < betOptions.size(); i++) {
            try {
                bettingButton = TestUtils.getRandomElementFromList(getBettingSelectionButtons());
            } catch (TimeoutException ex) {
                selectBetOptionFromDropdown(i);
            }
        }
        return bettingButton;
    }

    public boolean dropdownIsAvailable() {
        try {
            return betTypeDropdownArrow.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }

    }

    public boolean isSportsbookBetTableIsDisplayed() {
        try {
            TestUtils.waitForElementVisible(sportsbookTable);
            return sportsbookTable.isDisplayed();
        } catch (TimeoutException ex) {
            return false;
        }

    }

    public void changeBetType() {
        List<WebElement> betOptions = getBetTypeOptionsFromDropdown();
        if (betOptions.size() > 0) {
            betOptions.get(0).click();
        }
    }

    public String placeBetFromBetsTable() {

        List<WebElement> rows = sportsbookTable.findElements(By.tagName("tr"));
        int rowIndex = rows.size() - 1;

        WebElement selectedRow = rows.get(rowIndex);
        List<WebElement> bettingButtons = selectedRow.findElements(By.xpath(BET_BUTTONS_XPATH));

        if (!bettingButtons.isEmpty()) {
            WebElement randomBetButton = bettingButtons.get(random.nextInt(bettingButtons.size()));

            WebElement correspondingBetName = randomBetButton.findElement(By.xpath(BET_NAMES_RELATED_XPATH));
            betName = correspondingBetName.getText();
            logger.info(String.format("Placing a bet for '%s'", betName));

            randomBetButton.click();
        }
        return betName;

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

    public void selectBetOptionFromDropdown(int indexOfOptionToSelect) throws InterruptedException {
        WebElement betTypeDropdown = getBetTypeSelectionDropdown();
        hoverOverElement(betTypeDropdown);

        List<WebElement> betOptions = getBetTypeOptions();
        WebElement betOptionToSelect = betOptions.get(indexOfOptionToSelect);
        String betOptionName= betOptionToSelect.getText();
        logger.info(String.format("Selecting '%s' bet option from dropdown menu", betOptionName));
        TestUtils.explicitWait();
        betOptionToSelect.click();
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

    public EventPage clickOnRandomSportEvent(WebElement sportEventToClick) {
        TestUtils.scrollToElement(sportEventToClick);
        sportEventToClick.click();
        TestUtils.waitForElementVisible(sportsbookLogo);
        return new EventPage();
    }

    public WebElement getRandomSportEvent() {
        return TestUtils.getRandomElementFromList(sportEventLinks);
    }

    public boolean userIsOnLiveInGamesUrl() {
        return driver.getCurrentUrl().equals(TestUtils.baseUrl + TestUtils.liveInGameUrl);
    }
}
