package org.example.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class TestUtils  extends TestBase {
    private static final Logger logger = LogManager.getLogger(TestUtils.class);
    static JavascriptExecutor je;

    public static final String baseUrl = prop.getProperty("baseUrl");
    public static final String liveInGameUrl = prop.getProperty("liveGamesUrl");
    public static final String eventsPageUrl = prop.getProperty("eventUrl");
    public static final String leaguesPageUrl = prop.getProperty("leaguesUrl");
    protected static final Integer timeout = Integer.parseInt(prop.getProperty("waitTimeout"));
    protected static final Integer explicitTimeout = Integer.parseInt(prop.getProperty("explicitTimeout"));

    public static void takeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        logger.info("Current url is " + driver.getCurrentUrl());
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir + "/target/screenshots/" + System.currentTimeMillis() + ".png"));

    }

    public static int getRandomValueInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min value must be less than or equal to max value");
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static WebElement getRandomElementFromList(List<WebElement> listWithElements) {
        if (listWithElements.size() > 2) {
            return listWithElements.get(TestUtils.getRandomValueInRange(0, listWithElements.size()-1));
        }
        return listWithElements.get(0);
    }

    public static void explicitWait(){
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(explicitTimeout));
    }

    public static void scrollToElement(WebElement element) {
        je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);", element );
    }

    public static WebElement waitForElementVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementsVisible(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        return wait.until(ExpectedConditions.visibilityOf(elements.get(0)));
    }

    public static WebElement getElementByXpath(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public static WebElement getElementById(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
    }

}

