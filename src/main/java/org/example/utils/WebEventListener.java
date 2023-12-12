package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.apache.xalan.xsltc.compiler.sym.error;

public class WebEventListener extends TestBase implements WebDriverListener {
    private static final Logger logger = LogManager.getLogger(WebEventListener.class);

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        WebDriverListener.super.onError(target, method, args, e);

        try {
            logger.info("Exception occured: " + error);
            logger.info("Taking screenshot...");
            TestUtils.takeScreenshotAtEndOfTest();
            e.printStackTrace();
        } catch (IOException ex) {
            e.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        WebDriverListener.super.beforeGetCurrentUrl(driver);
        logger.info("Navigating to: " + url);
    }


}
