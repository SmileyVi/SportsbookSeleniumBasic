package org.example.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.WebEventListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;


public class TestBase {
    private static final Logger logger = LogManager.getLogger(TestBase.class);
    public static WebDriver driver;
    public static Properties prop;
    public static WebEventListener eventListener;
    public  static EventFiringDecorator e_driver;

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
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("'--remote-debugging-pipe");
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        }
        else if(browserName.equals("firefox")){
            driver = new FirefoxDriver();
        }
        logger.info("WebDriver is initialized...");

        eventListener = new WebEventListener();
        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(eventListener);
        driver = decorator.decorate(driver);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();



    }


}
