package com.smartaudit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SessionLimitPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Message shown on session limit page
    private By sessionMessage = By.xpath(
            "//*[contains(text(),'maximum number of simultaneous sessions')]"
    );

    // Radio button + submit button
    private By logoutRadio = By.xpath("//input[@type='radio']");
    private By submitButton = By.id("edit-submit");

    public SessionLimitPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // short wait
    }

    /**
     * ✅ This is the method your test is asking for
     */
    public boolean isSessionPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(sessionMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void logoutExistingSession() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutRadio)).click();
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }


}
