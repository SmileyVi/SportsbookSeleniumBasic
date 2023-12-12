package org.example.pages;

import org.openqa.selenium.support.PageFactory;

public class LeaguesPage extends BasePage {
    public LeaguesPage() {
        PageFactory.initElements(driver, this);
    }
}
