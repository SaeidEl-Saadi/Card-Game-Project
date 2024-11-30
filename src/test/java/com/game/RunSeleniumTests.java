package com.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class RunSeleniumTests {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testHomePageTitle() throws InterruptedException {
        // Open your local app
        driver.get("http://127.0.0.1:8081"); // Change to your local app URL

        // Get the page title
        String pageTitle = driver.getTitle();

        driver.findElement(By.id("button1")).click();

        // Assert that the title is as expected
        assertTrue(pageTitle.contains("Blackjack Game"));  // Replace with your app's title
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
