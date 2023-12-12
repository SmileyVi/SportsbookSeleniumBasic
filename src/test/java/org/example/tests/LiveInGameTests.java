package org.example.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.TestBase;
import org.example.pages.HomePage;
import org.example.pages.LiveInGamePage;
import org.example.pages.EventPage;
import org.example.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Live In-Game")
public class LiveInGameTests extends TestBase {
    HomePage homePage;
    LiveInGamePage liveInGamePage;
    EventPage eventPage;
    TestUtils testUtils;

    private static final Logger logger = LogManager.getLogger(LiveInGameTests.class);
    private String pageSource;

    public LiveInGameTests() {
        super();
    }

    @BeforeEach
    public void setUp() {
        initialization();
        testUtils = new TestUtils();
        liveInGamePage = new LiveInGamePage();
        eventPage = new EventPage();
        homePage = new HomePage();
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void verifySportEventsAreAvailable() {
        homePage.open(TestUtils.baseUrl);
        assertTrue(homePage.isSportsbookLogoDisplayed(), "Logo is not displayed.");
        homePage.acceptAllCookies();

        homePage.navigateToLiveInGamePage();
        assertTrue(liveInGamePage.userIsOnLiveInGamesUrl(), "Current URL is not expected!");

        assertTrue(liveInGamePage.atLeastOneSportEventIsDisplayed(),
                String.format("No events available for sport '%s'", liveInGamePage.selectRandomSport()));
    }

    @Test
    public void verifyBetTypeSelection() {
        homePage.open(TestUtils.baseUrl);
        assertTrue(homePage.isSportsbookLogoDisplayed(), "Logo is not displayed.");
        homePage.acceptAllCookies();

        homePage.navigateToLiveInGamePage();
        assertTrue(liveInGamePage.userIsOnLiveInGamesUrl(), "Current URL is not expected!");

        pageSource = liveInGamePage.getPageSource();
        liveInGamePage.changeBetType();

        assertFalse(pageSource.contentEquals(liveInGamePage.getPageSource()),
                "Page source remains the same after changing the bet type");
    }

    @Test
    public void verifyEventButtonRedirection() {
        homePage.open(TestUtils.baseUrl);
        assertTrue(homePage.isSportsbookLogoDisplayed(), "Logo is not displayed.");
        homePage.acceptAllCookies();

        homePage.navigateToLiveInGamePage();
        assertTrue(liveInGamePage.userIsOnLiveInGamesUrl(), "Current URL is not expected!");

        WebElement eventLink = liveInGamePage.getRandomSportEvent();
        String sportEventName = eventLink.getText();

        String urlOnLivePage = liveInGamePage.getCurrentUrl();

        eventPage = liveInGamePage.clickOnSportEvent(eventLink);

        assertNotEquals(urlOnLivePage, eventPage.getCurrentUrl(),
                "After click on sport event link, user is not redirected.");

        assertTrue(eventPage.isUrlContainsExpectedParams(TestUtils.eventsPageUrl, sportEventName),
                "User is not redirected to the events url");


        String headerOnEventPage = eventPage.getSectionHeader();
        assertTrue(headerOnEventPage.toLowerCase().contains(sportEventName.toLowerCase()),
                String.format("The displayed page does not contain the expected '%s' sport event name.", sportEventName));
    }



    @Test
    @Description("User try to place a bet before login")
    public void placeBetBeforeLogin() throws InterruptedException {
        String betName;
        homePage.open(TestUtils.baseUrl);
        assertTrue(homePage.isSportsbookLogoDisplayed(), "Logo is not displayed.");

        homePage.acceptAllCookies();
        homePage.navigateToLiveInGamePage();
        assertTrue(liveInGamePage.userIsOnLiveInGamesUrl(), "Current URL is not expected!");

        liveInGamePage.selectRandomSport();

        betName = liveInGamePage.placeBetOnRandomButton();
        assertTrue(liveInGamePage.betSlipIsAdded(), String.format("Expected Bet slip for '%s' is missing.", betName));
        assertTrue(liveInGamePage.loginToPlaceBetsButtonIsDisplayed(), "The button is either not enabled or with unexpected text");
    }
}
