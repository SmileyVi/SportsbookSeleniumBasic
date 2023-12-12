package org.example.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.TestUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;



public class TestBase {
    private static final Logger logger = LogManager.getLogger(TestBase.class);
    public static WebDriver driver;
    public static Properties prop;


    public TestBase(){
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/org/example"
                    + "/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initialization(){
        logger.info("Initializing WebDriver...");
        String browserName = prop.getProperty("browser");

        if(browserName.equals("chrome")){
            driver = new ChromeDriver();
        }
        else if(browserName.equals("firefox")){
            driver = new FirefoxDriver();
        }
        logger.info("WebDriver is initialized...");

//        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with EventFiringWebDriver
//        eventListener = new WebEventListener();
//        e_driver.register(eventListener);
//        driver = e_driver;

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        //driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);

       // driver.get(prop.getProperty("url"));

    }









}
