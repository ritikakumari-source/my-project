package com.smartaudit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuditsPage {

    WebDriver driver;
    WebDriverWait wait;

    // 🔑 REAL audits page identifier (most reliable)
    By auditIdSearch = By.xpath("//label[contains(text(),'Search By Audit Id')]/following::input[1]");
    private By auditSearchInput = By.id("edit-comp-vendor-unit-filter");
    private By filterButton = By.id("edit-submit");
    private By resultTable = By.xpath("//table");
    private By noResultMsg = By.xpath("//*[contains(text(),'No results')]");
    private By auditStatusDropdown = By.name("audit_status[]");
    public AuditsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public boolean isAuditsPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(auditIdSearch));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void searchByCompanyOrVendor(String value) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(auditSearchInput)
        );
        input.clear();
        input.sendKeys(value);
    }

    public void clickFilterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
    }

    public boolean isSearchResultDisplayed() {
        try {
            wait.until(driver ->
                    driver.findElements(resultTable).size() > 0 ||
                            driver.findElements(noResultMsg).size() > 0
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
