package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.support.PageFactory;

public class EventPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(EventPage.class);

    public EventPage() {
        PageFactory.initElements(driver, this);
    }

}
