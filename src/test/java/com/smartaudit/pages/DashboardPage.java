/*
package com.smartaudit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;
    By searchInput = By.id("edit-comp-vendor-unit-filter");
    By filterButton = By.id("edit-submit");
    private By companyVendorSearch =
            By.id("edit-comp-vendor-unit-filter");

    private By resultsTable =
            By.cssSelector("table"); // adjust later if needed

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }


    public boolean isDashboardLoaded() {
        // Dashboard is confirmed by URL, not by filter fields
        return driver.getCurrentUrl().contains("dashboard")
                || driver.getCurrentUrl().contains("audit");
    }


    // 🔍 Search by Company / Unit / Vendor
    public void searchByCompanyOrVendor(String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(companyVendorSearch))
                .clear();
        driver.findElement(companyVendorSearch).sendKeys(value);

    }

    public void enterSearchText(String text) {
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );
        search.clear();
        search.sendKeys(text);
    }

    // 🔘 Click Filter button
    public void clickFilterButton() {
        WebElement filter = wait.until(
                ExpectedConditions.elementToBeClickable(filterButton)
        );
        filter.click();
    }
}
*/
package com.smartaudit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    private By auditsPageIdentifier = By.xpath(
            "//a[contains(@href,'/admin/myndsol-audit/manage/audit')]"
    );
    private By auditIdSearchInput =
            By.xpath("//label[contains(text(),'Search By Audit Id')]/following::input[1]");

    // Top menu - Audits
    By auditsMenu = By.cssSelector("a[href='/admin/myndsol-audit']");
    // Audits page heading
    private By auditsPageHeading = By.xpath("//h1[contains(text(),'Audits')]");
    // Top menu
    private By auditsListing = By.xpath("//a[contains(@href,'/admin/myndsol-audit')]");
    // Dashboard validation
    private By dashboardHeader = By.xpath("//h1[contains(text(),'Dashboard')]");
    // Audit Status dropdown
    private By auditStatusDropdown = By.name("audit_status[]");
    // Reset button
    private By resetButton = By.id("edit-reset");
    // Filter button
    private By filterButton = By.id("edit-submit");
    // Search input
    private By searchInput = By.id("edit-comp-vendor-unit-filter");
    // Result table (adjust if needed)
    private By resultTable = By.xpath("//table[contains(@class,'table')]");
    private By noResultMsg = By.xpath("//*[contains(text(),'No results')]");
    //Dashboard identifier
    private By dashboardMarker = By.xpath("//*[contains(text(),'Dashboard')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public boolean isDashboardLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ✅ NEW FLOW ADDED
    public void navigateToAuditsPage() {
        WebElement audits = wait.until(
                ExpectedConditions.elementToBeClickable(auditsMenu)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", audits);
    }


    public boolean isAuditsPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(auditsPageIdentifier));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void searchByAuditId(String auditId) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(auditIdSearchInput)
        );
        input.clear();
        input.sendKeys(auditId);
    }

    public void searchByCompanyOrVendor(String value) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );
        input.clear();
        input.sendKeys(value);
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


    public void clickFilterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
    }

    public void resetFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
        System.out.println("Filters reset on Dashboard Page");
    }

    public void selectAuditStatus(String statusText) {

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(auditStatusDropdown)
        );

        Select select = new Select(dropdown);

        // because dropdown is MULTIPLE
        select.deselectAll();
        select.selectByVisibleText(statusText);
    }

    // Company / Vendor column
    private By vendorColumn = By.xpath("//table/tbody/tr/td[4]");

    public boolean allRowsHaveVendor(String vendorName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(vendorColumn));

        for (WebElement vendor : driver.findElements(vendorColumn)) {
            if (!vendor.getText().toLowerCase().contains(vendorName.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    // Audit Status column
    private By auditStatusColumn = By.xpath("//table/tbody/tr/td[6]");

    public boolean allRowsHaveAuditStatus(String expectedStatus) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(auditStatusColumn));

        for (WebElement status : driver.findElements(auditStatusColumn)) {
            if (!status.getText().equalsIgnoreCase(expectedStatus)) {
                return false;
            }
        }
        return true;
    }


}
