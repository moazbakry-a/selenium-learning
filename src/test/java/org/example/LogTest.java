package org.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LogTest extends BaseTest{


    @Test(dataProvider = "credentials")
    public void loginTest(String username, String password,
                          String expectedPath, String expectedMessage) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, password);

        Assert.assertTrue(driver.getCurrentUrl().endsWith(expectedPath),
                "Unexpected Path: " + driver.getCurrentUrl());

        if (expectedPath.equals("/secure")) {
            SecurePage securePage = new SecurePage(driver);
            Assert.assertEquals(securePage.getHeadingText(), "Secure Area");
            String actualMessage = securePage.getFlashMessageText();
            Assert.assertTrue(actualMessage.contains(expectedMessage),
                    "Unexpected message " + actualMessage);
        }
        else {
            String actualMessage = loginPage.getFlashMessageText();
            Assert.assertTrue(actualMessage.contains(expectedMessage),
                    "Error message " + actualMessage);
        }
    }
    @DataProvider(name = "credentials")
    public Object[][] credentials() {
        return new Object[][]{
                {"tomsmith", "SuperSecretPassword!",
                        "/secure", "You logged into a secure area!"},
                {"Tomsmith", "SuperSecretPassword!",
                        "/login", "THIS MESSAGE SHOULD NOT APPEAR"},
                {"tomsmith", "superSecretPassword!",
                        "/login", "Your password is invalid!"}
        };
    }


}
