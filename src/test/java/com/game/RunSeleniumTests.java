package com.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class RunSeleniumTests {

    private WebDriver driver;
    private int waitTime = 1000;
    private int previous = waitTime;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
    }

    @Test
    public void A1_Scenario() throws InterruptedException {
        // Open local app
        driver.get("http://127.0.0.1:8081");
        Thread.sleep(500);
        driver.findElement(By.id("startA1")).click();
        Thread.sleep(waitTime);

        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        continueButton.click();
        delay();
        continueButton.click();
        delay();
        inputBox.sendKeys("2"); //P1 declines sponsor
        delay();
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 sponsors
        delay();
        continueButton.click();
        delay();
        //P2 stages F5, H10 for stage 1
        stageCards("F5,H10", "p2");
        //P2 stages F15, S10 for stage 2
        stageCards("F15,S10", "p2");
        //P2 stages F15, D5, B15 for stage 3
        stageCards("F15,D5,B15", "p2");
        //P2 stages F40, B15 for stage 4
        stageCards("F40,B15", "p2");

        //stage 1
        inputBox.sendKeys("1"); //P1 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 100;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        trimHand("F5", "p1");
        trimHand("F5", "p3");
        trimHand("F5", "p4");

        //P1 attacks with D5,S10
        stageCards("D5,S10", "p1");
        //P3 attacks with S10,D5
        stageCards("S10,D5", "p3");
        //P4 attacks with D5,H10
        stageCards("D5,H10", "p4");

        //stage 2
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P1 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 100;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        //P1 attacks with H10,S10
        stageCards("H10,S10", "p1");
        //P3 attacks with B15,S10
        stageCards("B15,S10", "p3");
        //P4 attacks with H10,B15
        stageCards("H10,B15", "p4");


        //stage 3
        continueButton.click();

        Assertions.assertEquals("F5, F10, F15, F15, F30, H10, B15, B15, L20, ", driver.findElement(By.id("p1")).getAttribute("value"));
        Assertions.assertEquals("0", driver.findElement(By.id("p1Shields")).getText());
        delay();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 100;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        //P3 attacks with L20,H10,S10
        stageCards("L20,H10,S10", "p3");
        //P4 attacks with B15,S10,L20
        stageCards("B15,S10,L20", "p4");

        //stage 4
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 100;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        //P3 attacks with B15,H10,L20
        stageCards("B15,H10,L20", "p3");
        //P4 attacks with D5,S10,L20,E30
        stageCards("D5,S10,L20,E30", "p4");
        continueButton.click();

        Assertions.assertEquals("5", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("F5, F5, F15, F30, S10, ", driver.findElement(By.id("p3")).getAttribute("value"));
        Assertions.assertEquals("0", driver.findElement(By.id("p3Shields")).getText());

        Assertions.assertEquals("F15, F15, F40, L20, ", driver.findElement(By.id("p4")).getAttribute("value"));
        Assertions.assertEquals("4", driver.findElement(By.id("p4Shields")).getText());

        waitTime = 100;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        trimRandom("p2");

        Assertions.assertEquals("12", driver.findElement(By.id("p2Cards")).getText());

        driver.findElement(By.id("resetButton")).click();
    }

    @Test
    public void second_Scenario() throws InterruptedException {
        // Open local app
        driver.get("http://127.0.0.1:8081");
        Thread.sleep(500);
        driver.findElement(By.id("second_Scenario")).click();
        Thread.sleep(waitTime);

        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        continueButton.click();
        delay();
        continueButton.click();
        delay();

        inputBox.sendKeys("1"); //P1 sponsor
        delay();
        continueButton.click();
        delay();

        //P1 stages F5 for stage 1
        stageCards("F5", "p1");
        //P1 stages F15, D5 for stage 2
        stageCards("F5,D5", "p1");
        //P1 stages F10,H10 for stage 3
        stageCards("F10,H10", "p1");
        //P1 stages F10,B15 for stage 4
        stageCards("F10,B15", "p1");

        //stage 1
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        trimHand("F5", "p2");
        trimHand("F5", "p3");
        trimHand("F10", "p4");

        //P2 attacks with H10
        stageCards("H10", "p2");
        //P3 attacks with nothing
        inputBox.sendKeys("quit");
        delay();
        continueButton.click();
        //P4 attacks with H10
        stageCards("H10", "p4");

        //stage 2
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        //P2 attacks with S10
        stageCards("S10", "p2");
        //P4 attacks with S10
        stageCards("S10", "p4");

        //stage 3
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        //P2 attacks with H10,S10
        stageCards("H10,S10", "p2");
        //P4 attacks with H10,S10
        stageCards("H10,S10", "p4");

        //stage 4
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        //P2 attacks with S10,B15
        stageCards("S10,B15", "p2");
        //P4 attacks with S10,B15
        stageCards("S10,B15", "p4");
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        trimHand("F5,F10,F15,F15", "p1");

        //P2 TURN
        continueButton.click();
        delay();
        continueButton.click();
        delay();

        inputBox.sendKeys("2"); //P2 declines sponsor
        delay();
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P3 sponsors
        delay();
        continueButton.click();
        delay();

        //P3 stages F5 for stage 1
        stageCards("F5", "p3");
        //P3 stages F5,D5 for stage 2
        stageCards("F5,D5", "p3");
        //P3 stages F5,H10 for stage 3
        stageCards("F5,H10", "p3");

        //stage 1
        inputBox.sendKeys("2"); //P1 declines to participate
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with D5
        stageCards("D5", "p2");
        //P4 attacks with D5
        stageCards("D5", "p4");

        //stage 2
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with B15
        stageCards("B15", "p2");
        //P4 attacks with B15
        stageCards("B15", "p4");

        //stage 3
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with E30
        stageCards("E30", "p2");
        //P4 attacks with E30
        stageCards("E30", "p4");
        continueButton.click();

        waitTime = 200;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        trimHand("F20,F25,F30", "p3");
        delay();
        continueButton.click();
        delay();

        //P2 and P4 and winners
        Assertions.assertEquals("7", driver.findElement(By.id("p2Shields")).getText());
        Assertions.assertEquals("7", driver.findElement(By.id("p4Shields")).getText());

        //hand assertions
        Assertions.assertEquals("12", driver.findElement(By.id("p1Cards")).getText());
        Assertions.assertEquals("F15, F15, F20, F20, F20, F20, F25, F25, F30, H10, B15, L20, ", driver.findElement(By.id("p1")).getAttribute("value"));

        Assertions.assertEquals("9", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("F10, F15, F15, F25, F30, F40, F50, L20, L20, ", driver.findElement(By.id("p2")).getAttribute("value"));

        Assertions.assertEquals("12", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("F20, F40, D5, D5, S10, H10, H10, H10, H10, B15, B15, L20, ", driver.findElement(By.id("p3")).getAttribute("value"));

        Assertions.assertEquals("9", driver.findElement(By.id("p4Cards")).getText());
        Assertions.assertEquals("F15, F15, F20, F25, F30, F50, F70, L20, L20, ", driver.findElement(By.id("p4")).getAttribute("value"));

        driver.findElement(By.id("resetButton")).click();
    }

    @Test
    public void third_Scenario() throws InterruptedException {
        // Open local app
        driver.get("http://127.0.0.1:8081");
        Thread.sleep(500);
        driver.findElement(By.id("third_Scenario")).click();
        Thread.sleep(waitTime);

        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        continueButton.click();
        delay();
        continueButton.click();
        delay();

        inputBox.sendKeys("1"); //P1 sponsor
        delay();
        continueButton.click();
        delay();

        //P1 stages F5 for stage 1
        stageCards("F5", "p1");
        //P1 stages F10 for stage 2
        stageCards("F10", "p1");
        //P1 stages F15 for stage 3
        stageCards("F15", "p1");
        //P1 stages F20 for stage 4
        stageCards("F20", "p1");

        //stage 1
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        trimHand("F5", "p2");
        trimHand("F10", "p3");
        trimHand("F20", "p4");

        //P2 attacks with H10
        stageCards("S10", "p2");
        //P3 attacks with nothing
        stageCards("S10", "p3");
        //P4 attacks with H10
        stageCards("S10", "p4");

        Assertions.assertEquals("11", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p4Cards")).getText());

        //stage 2
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with H10
        stageCards("H10", "p2");
        //P3 attacks with nothing
        stageCards("H10", "p3");
        //P4 attacks with H10
        stageCards("H10", "p4");

        Assertions.assertEquals("11", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p4Cards")).getText());

        //stage 3
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with H10
        stageCards("B15", "p2");
        //P3 attacks with nothing
        stageCards("B15", "p3");
        //P4 attacks with H10
        stageCards("B15", "p4");

        Assertions.assertEquals("11", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p4Cards")).getText());

        //stage 4
        continueButton.click();
        delay();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with H10
        stageCards("L20", "p2");
        //P3 attacks with nothing
        stageCards("L20", "p3");
        //P4 attacks with H10
        stageCards("L20", "p4");
        continueButton.click();

        Assertions.assertEquals("11", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("11", driver.findElement(By.id("p4Cards")).getText());

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        trimHand("F5,F5,F10,F10", "p1");

        //P2 TURN
        continueButton.click();
        delay();
        continueButton.click();
        delay();

        //P3 TURN
        continueButton.click();
        delay();
        continueButton.click();
        delay();

        waitTime = 300;
        continueButton.click();
        delay();
        waitTime = previous;

        trimHand("F5,F10", "p1");
        trimHand("F5", "p2");
        trimHand("F5", "p3");
        trimHand("F20", "p4");

        //P4 TURN
        continueButton.click();
        delay();
        continueButton.click();
        delay();

        waitTime = 300;
        continueButton.click();
        delay();
        waitTime = previous;

        trimHand("F25,F30", "p4");

        //P1 TURN
        continueButton.click();
        delay();
        continueButton.click();
        delay();

        inputBox.sendKeys("1"); //P1 sponsor
        delay();
        continueButton.click();
        delay();

        //P1 stages F5 for stage 1
        stageCards("F15", "p1");
        //P1 stages F10 for stage 2
        stageCards("F15,D5", "p1");
        //P1 stages F15 for stage 3
        stageCards("F20,D5", "p1");

        //stage 1
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        trimHand("F5", "p2");
        trimHand("F10", "p3");
        trimHand("F20", "p4");

        //P2 attacks with 15
        stageCards("B15", "p2");
        //P3 attacks with 15
        stageCards("B15", "p3");
        //P4 attacks with H10
        stageCards("H10", "p4");

        Assertions.assertEquals("11", driver.findElement(By.id("p4Cards")).getText());

        //stage 2
        continueButton.click();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with 15
        stageCards("B15,H10", "p2");
        //P3 attacks with 15
        stageCards("B15,S10", "p3");

        Assertions.assertEquals("10", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("10", driver.findElement(By.id("p3Cards")).getText());

        //stage 3
        continueButton.click();
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        //P2 attacks with 15
        stageCards("L20,S10", "p2");
        //P3 attacks with 15
        stageCards("E30", "p3");
        continueButton.click();

        Assertions.assertEquals("15", driver.findElement(By.id("p1Cards")).getText());
        Assertions.assertEquals("9", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("10", driver.findElement(By.id("p3Cards")).getText());

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        delay();
        waitTime = previous;

        trimHand("F15,F15,F15", "p1");
        delay();
        continueButton.click();
        delay();

        //P3 is a winner
        Assertions.assertEquals("7", driver.findElement(By.id("p3Shields")).getText());

        //hand assertions
        Assertions.assertEquals("12", driver.findElement(By.id("p1Cards")).getText());
        Assertions.assertEquals("F25, F25, F35, D5, D5, S10, S10, S10, S10, H10, H10, H10, ", driver.findElement(By.id("p1")).getAttribute("value"));

        Assertions.assertEquals("9", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("F15, F25, F30, F40, S10, S10, S10, H10, E30, ", driver.findElement(By.id("p2")).getAttribute("value"));

        Assertions.assertEquals("10", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("F10, F25, F30, F40, F50, S10, S10, H10, H10, L20, ", driver.findElement(By.id("p3")).getAttribute("value"));

        Assertions.assertEquals("11", driver.findElement(By.id("p4Cards")).getText());
        Assertions.assertEquals("F25, F25, F30, F50, F70, D5, D5, S10, S10, B15, L20, ", driver.findElement(By.id("p4")).getAttribute("value"));

        delay();
        driver.findElement(By.id("resetButton")).click();
    }

    @Test
    public void fourth_Scenario() throws InterruptedException {
        // Open local app
        driver.get("http://127.0.0.1:8081");
        Thread.sleep(500);
        driver.findElement(By.id("fourth_Scenario")).click();
        Thread.sleep(waitTime);

        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        continueButton.click();
        delay();
        continueButton.click();
        delay();

        inputBox.sendKeys("1"); //P1 sponsor
        delay();
        continueButton.click();
        delay();

        //P1 stages F5 for stage 1
        stageCards("F50,D5,S10,H10,B15,L20", "p1");
        //P1 stages F10 for stage 2
        stageCards("F70,D5,S10,H10,B15,L20", "p1");

        //stage 1
        inputBox.sendKeys("1"); //P2 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P3 participates
        delay();
        continueButton.click();
        inputBox.sendKeys("1"); //P4 participates
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        trimHand("F5", "p2");
        trimHand("F15", "p3");
        trimHand("F10", "p4");

        //P2 attacks with H10
        stageCards("E30", "p2");
        //P3 attacks with nothing
        inputBox.sendKeys("quit");
        delay();
        continueButton.click();
        //P4 attacks with nothing
        inputBox.sendKeys("quit");
        delay();
        continueButton.click();
        delay();
        continueButton.click();

        waitTime = 300;
        continueButton.click();
        delay();
        continueButton.click();
        waitTime = previous;

        trimHand("F5,F10", "p1");
        delay();

        //hand assertions
        Assertions.assertEquals("12", driver.findElement(By.id("p1Cards")).getText());
        Assertions.assertEquals("F15, D5, D5, D5, D5, S10, S10, S10, H10, H10, H10, H10, ", driver.findElement(By.id("p1")).getAttribute("value"));

        Assertions.assertEquals("11", driver.findElement(By.id("p2Cards")).getText());
        Assertions.assertEquals("F5, F5, F10, F15, F15, F20, F20, F25, F30, F30, F40, ", driver.findElement(By.id("p2")).getAttribute("value"));

        Assertions.assertEquals("12", driver.findElement(By.id("p3Cards")).getText());
        Assertions.assertEquals("F5, F5, F10, F15, F15, F20, F20, F25, F25, F30, F40, L20, ", driver.findElement(By.id("p3")).getAttribute("value"));

        Assertions.assertEquals("12", driver.findElement(By.id("p4Cards")).getText());
        Assertions.assertEquals("F5, F5, F10, F15, F15, F20, F20, F25, F25, F30, F50, E30, ", driver.findElement(By.id("p4")).getAttribute("value"));

        driver.findElement(By.id("resetButton")).click();
    }

    private int getCard(String card, String hand) {
        String[] arr = hand.split(",\\s*");
        if (arr.length == 0) return -1;
        ArrayList<String> cards = new ArrayList<>(List.of(arr));

        int cardNum = 0;
        for (String s : cards) {
            if (!s.isBlank()) {
                cardNum++;
                if (s.equals(card)) {
                    return cardNum;
                }
            }
        }

        return -1;
    }

    private void stageCards(String cards, String player) throws InterruptedException {
        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        String[] arr = cards.split(",");

        for (String s : arr) {
            inputBox.sendKeys("" + getCard(s ,driver.findElement(By.id(player)).getAttribute("value")));
            delay();
            continueButton.click();
            delay();
        }

        inputBox.sendKeys("quit");
        delay();
        continueButton.click();
        delay();
    }

    private void trimHand(String cards, String player) throws InterruptedException {
        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        String[] arr = cards.split(",");

        for (String s : arr) {
            inputBox.sendKeys("1");
            Thread.sleep(800);
            continueButton.click();
            Thread.sleep(800);
            inputBox.sendKeys("" + getCard(s ,driver.findElement(By.id(player)).getAttribute("value")));
            Thread.sleep(800);
            continueButton.click();
            Thread.sleep(800);
        }


    }

    private void trimRandom(String player) throws InterruptedException {
        WebElement inputBox = driver.findElement(By.className("inputBox"));
        WebElement continueButton = driver.findElement(By.id("continue"));

        while (Integer.parseInt(driver.findElement(By.id(player + "Cards")).getText()) > 12) {
            inputBox.sendKeys("1");
            Thread.sleep(800);
            continueButton.click();
            Thread.sleep(800);
        }


    }

    private void delay() throws InterruptedException {
        Thread.sleep(waitTime);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
