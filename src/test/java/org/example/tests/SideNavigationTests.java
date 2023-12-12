package org.example.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.TestBase;
import org.example.pages.HomePage;
import org.example.pages.LeaguesPage;
import org.example.pages.SideNavigation;
import org.example.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SideNavigationTests extends TestBase {
    SideNavigation sideNavigation;
    HomePage homePage;
    LeaguesPage leaguesPage;
    TestUtils testUtils;

    private final Integer timeout = Integer.parseInt(prop.getProperty("waitTimeout"));

    private static final Logger logger = LogManager.getLogger(LiveInGameTests.class);

    static final String A_TO_Z_NAVIGATION_CATEGORY_NAME = "A-Z SPORTS";
    private String pageSource;

    public SideNavigationTests() {
        super();
    }

    @BeforeEach
    public void setUp() {
        initialization();
        testUtils = new TestUtils();
        homePage = new HomePage();
        sideNavigation = new SideNavigation();
        leaguesPage = new LeaguesPage();
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void verifyAToZSportsNavigationPanel() {
        homePage.open(TestUtils.baseUrl);
        assertTrue(homePage.isSportsbookLogoDisplayed(), "Logo is not displayed.");
        homePage.acceptAllCookies();

        assertTrue(sideNavigation.expandAtoZSportsNavigation(), "Category not expanded");

        assertTrue(sideNavigation.sportsInNavCategoryAreAvailable(),
                String.format("There are no any sports available in '%s' navigation panel!", A_TO_Z_NAVIGATION_CATEGORY_NAME));

        assertTrue(sideNavigation.expandSportInNavigationPanel(), "Sport in A-Z sports is not expanded");

        assertTrue(sideNavigation.sportLeaguesAreAvailable(), "Leagues are not available");

    }

    @Test
    public void navigateToSportLeague() {
        homePage.open(TestUtils.baseUrl);
        assertTrue(homePage.isSportsbookLogoDisplayed(), "Logo is not displayed.");
        homePage.acceptAllCookies();

        String urlBeforeNavigation = driver.getCurrentUrl();

        leaguesPage = sideNavigation.navigateToSportLeague();

        String leagueName = sideNavigation.getLeagueName();

        assertNotEquals(urlBeforeNavigation, leaguesPage.getCurrentUrl(),
                "After click on sport event link, user is not redirected.");

        assertTrue(leaguesPage.isUrlContainsExpectedParams(TestUtils.leaguesPageUrl, leagueName),
                String.format("URL is unexpected- '%s'. Expected params: '%s' and '%s' are missing",
                        leaguesPage.getCurrentUrl(), TestUtils.leaguesPageUrl, leagueName));

        String headerOnLeaguesPage = leaguesPage.getSectionHeader();

        assertTrue(headerOnLeaguesPage.toLowerCase().contains(headerOnLeaguesPage.toLowerCase()),
                String.format("The displayed page does not contain the expected '%s' sport event name.", headerOnLeaguesPage));

    }

}
