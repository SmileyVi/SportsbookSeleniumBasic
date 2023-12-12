package org.example.utils;

import org.example.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.IOException;

import static org.apache.xalan.xsltc.compiler.sym.error;

public class WebEventListener extends TestBase implements WebDriverListener {


    public void beforeNavigateTo(String url, WebDriver driver) {
        System.out.println("Before navigating to: '" + url + "'");
    }


    public void onException(Throwable throwable, WebDriver driver) {
        System.out.println("Exception occured: " + error);
        try {
            TestUtils.takeScreenshotAtEndOfTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
