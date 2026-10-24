package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage extends BasePage {
    private final By usernamefield = By.id("username");
    private final By passwordfield = By.id("password");
    private final By loginbutton = By.className("radius");
    private final By flashmessage = By.id("flash");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public void open(){
        driver.get(Config.BASE_URL + "/login");
    }

    public void login(String username, String password){
        driver.findElement(usernamefield).sendKeys(username);
        driver.findElement(passwordfield).sendKeys(password);
        waitForClickable(loginbutton).click();
    }
    public String getFlashMessageText(){
        return  getText(flashmessage);
    }

}
