package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SecurePage extends BasePage{

    private final By heading = By.cssSelector("div.example h2");
    private final By flashMessage = By.id("flash");

    public SecurePage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getText(heading);
    }
    public String getFlashMessageText(){
        return  getText(flashMessage);
    }
}
