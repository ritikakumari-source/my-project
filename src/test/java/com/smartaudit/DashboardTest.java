package com.smartaudit;

import com.smartaudit.base.BaseTest;
import com.smartaudit.pages.AuditsPage;
import com.smartaudit.pages.DashboardPage;
import com.smartaudit.pages.LoginPage;
import com.smartaudit.pages.SessionLimitPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test
    public void searchByCompanyOrVendor() {

        // 1️⃣ Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("ritika12", "ritika123");

        // 2️⃣ Check session limit
        SessionLimitPage sessionPage = new SessionLimitPage(driver);

        if (sessionPage.isSessionPageDisplayed()) {

            System.out.println("⚠ Session limit detected");

            // Handle session
            sessionPage.logoutExistingSession();

            // ✅ WAIT & VERIFY redirect to login
            Assert.assertTrue(
                    loginPage.isLoginPageDisplayed(),
                    "User was NOT redirected to login page after session handling"
            );

            System.out.println("✅ Redirected to login page after session handling");
            return; //
        }

        // 3️⃣ Continue ONLY if no session page
        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard not loaded"
        );
        // 4️⃣ Navigate to Audits page
        dashboardPage.navigateToAuditsPage();

        AuditsPage auditsPage = new AuditsPage(driver);
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Search field present: " +
                driver.findElements(By.id("edit-comp-vendor-unit-filter")).size());


        Assert.assertTrue(
                auditsPage.isAuditsPageLoaded(),
                "Audits page not loaded"
        );
        dashboardPage.searchByAuditId("16024");
        dashboardPage.clickFilterButton();

        Assert.assertTrue(
                dashboardPage.isSearchResultDisplayed(),
                "No results shown after filter"
        );
        dashboardPage.searchByCompanyOrVendor("Demo");
        dashboardPage.clickFilterButton();

        Assert.assertTrue(
                dashboardPage.isSearchResultDisplayed(),
                "No results shown after filter"
        );

        System.out.println("✅ Dashboard flow successful");
    }
}
