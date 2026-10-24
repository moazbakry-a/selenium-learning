package org.example;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseTest {
    protected WebDriver driver;


    @BeforeMethod
    public void setUp() {
        if (Config.BROWSER.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if ("true".equalsIgnoreCase(System.getenv("CI"))) {
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);

        } else if (Config.BROWSER.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser " + Config.BROWSER);
        }
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (driver != null) {

            if (result.getStatus() == ITestResult.FAILURE) {
                saveScreenshot(result.getMethod().getMethodName()
                        + "_" + result.getStartMillis());
            }

            driver.quit();
        }
    }

    private void saveScreenshot(String testName) {

        try {
            Path folder = Path.of("target", "screenshots");    // folderPath
            Files.createDirectories(folder);                   // create the folder

            Path file = folder.resolve(testName + ".png");     // filePath

            byte[] screenshot = ((TakesScreenshot) driver)     // screenshot and its dataType
                    .getScreenshotAs(OutputType.BYTES);

            Files.write(file, screenshot);           // create the file

            System.out.println(
                    "Screenshot saved: " + file.toAbsolutePath()
            );

        } catch (IOException e) {
            System.err.println(
                    "Could not save screenshot: " + e.getMessage()
            );
        }
    }
}


