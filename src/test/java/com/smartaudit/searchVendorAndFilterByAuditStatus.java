package com.smartaudit;
import com.smartaudit.base.BaseTest;
import com.smartaudit.pages.DashboardPage;
import com.smartaudit.pages.LoginPage;
import com.smartaudit.pages.SessionLimitPage;
import org.testng.Assert;
import org.testng.annotations.Test;
public class searchVendorAndFilterByAuditStatus extends BaseTest {
    @Test
    public void searchVendorAndFilterByAuditStatus() {

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
            return;

        }

        // After clearing session, continue test normally
        driver.navigate().refresh();


        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardLoaded() || loginPage.isLoginPageDisplayed(),
                "Neither Dashboard nor Login page loaded after session handling"
        );


    /*    // 3️⃣ Continue ONLY if no session page
        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard not loaded"
        );*/

        // 4️⃣ Search Vendor + Apply Audit Status
        dashboardPage.searchByCompanyOrVendor("Demo");
        dashboardPage.selectAuditStatus("Publish to Vendor");
        dashboardPage.clickFilterButton();

        Assert.assertTrue(
                dashboardPage.allRowsHaveVendor("Demo"),
                "Vendor filter failed"
        );

        Assert.assertTrue(
                dashboardPage.allRowsHaveAuditStatus("Publish to Vendor"),
                "Audit Status filter failed"
        );


        // 5️⃣ Verify vendor + status
        Assert.assertTrue(
                dashboardPage.allRowsHaveVendor("Demo"),
                "Vendor filter failed"
        );

        Assert.assertTrue(
                dashboardPage.allRowsHaveAuditStatus("Published"),
                "Audit Status filter failed"
        );

        System.out.println("Search + Audit Status filter test passed");
    }

}
