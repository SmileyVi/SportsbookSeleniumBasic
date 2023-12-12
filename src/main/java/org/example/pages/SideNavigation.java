package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SideNavigation extends BasePage {
    public SideNavigation() {
        PageFactory.initElements(driver, this);
    }

    private final Logger logger = LogManager.getLogger(SideNavigation.class);

    List<WebElement> leagues;
    WebElement league;
    private static final String LEAGUES_LOCATOR = "//div[contains(@aria-label,'%s')]/..//a";
    static final By A_TO_Z_SPORTS_CSS_LOCATOR = By.xpath("//div[@aria-label='Expandable container A-Z SPORTS']");
    static final By SPORTS_IN_CATEGORIES_CSS_LOCATOR = By.xpath(
            "//div[starts-with(@aria-label,'Expandable container')]");

    @FindBy(css = "div[aria-label='Expandable container A-Z SPORTS'")
    WebElement aToZSportsNavigationPanel;

    @FindBy(css = "div[aria-label='Expandable container A-Z SPORTS'] ~ div > div > div")
    List<WebElement> aToZASportsInNavigationPanel;


    public boolean expandAtoZSportsNavigation() {
        if (aToZSportsNavigationPanel.getAttribute("aria-expanded").equals("false")) {
            aToZSportsNavigationPanel.click();
        }
        return aToZSportsNavigationPanel.getAttribute("aria-expanded").equals("true");
    }

    public boolean sportsInNavCategoryAreAvailable() {
        return aToZASportsInNavigationPanel.size() > 0;
    }

    private List<WebElement> getSportsInAToZCategory () {
        WebElement navigationCategory = driver.findElement(A_TO_Z_SPORTS_CSS_LOCATOR);
        return navigationCategory.findElements(SPORTS_IN_CATEGORIES_CSS_LOCATOR);
    }

    public boolean expandSportInNavigationPanel() {
        WebElement sport = TestUtils.getRandomElementFromList(getSportsInAToZCategory());

        if (sport.getAttribute("aria-expanded").equals("false")) {
            TestUtils.scrollToElement(sport);
            logger.info("Expanding Sport " + sport.getText());
            sport.click();
        }
        leagues = getLeaguesForSport(sport);
        return sport.getAttribute("aria-expanded").equals("true");
    }

    private List<WebElement> getLeaguesForSport(WebElement sport) {
        String sportName = sport.getText();
        final By leaguesLocatorForSport = By.xpath(String.format(LEAGUES_LOCATOR, sportName));

        return sport.findElements(leaguesLocatorForSport);
    }

    public boolean sportLeaguesAreAvailable() {
        return leagues.size() > 0 &&
                leagues.get(0).isDisplayed();
    }

    public LeaguesPage navigateToSportLeague() {
        expandAtoZSportsNavigation();
        expandSportInNavigationPanel();
        league = leagues.get(0);
        TestUtils.scrollToElement(league);
        league.click();
        return new LeaguesPage();
    }

    public String getLeagueName() {
        return league.getText();
    }

}
