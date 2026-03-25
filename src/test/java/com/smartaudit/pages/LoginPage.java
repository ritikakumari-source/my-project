package com.smartaudit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("edit-name");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField))
                .sendKeys(username);
        driver.findElement(By.id("edit-pass")).sendKeys(password);
        driver.findElement(By.id("edit-submit")).click();
    }

    // ✅ MUST WAIT for redirect
    public boolean isLoginPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
