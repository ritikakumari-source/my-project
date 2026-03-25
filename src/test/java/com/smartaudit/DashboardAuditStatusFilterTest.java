package com.smartaudit;

import com.smartaudit.base.BaseTest;
import com.smartaudit.pages.DashboardPage;
import com.smartaudit.pages.LoginPage;
import com.smartaudit.pages.SessionLimitPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardAuditStatusFilterTest extends BaseTest {

    @Test
    public void filterByAuditStatus() {

        // 1️⃣ Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("ritika12", "ritika123");

        // 2️⃣ Session check
        SessionLimitPage sessionPage = new SessionLimitPage(driver);

        if (sessionPage.isSessionPageDisplayed()) {

            System.out.println("Session limit detected");
            sessionPage.logoutExistingSession();

            Assert.assertTrue(
                    loginPage.isLoginPageDisplayed(),
                    "Not redirected to login after session handling"
            );

            return; // 🛑 STOP test
        }

        // 3️⃣ Dashboard
        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard not loaded"
        );

        // 🔄 Reset before applying filter (IMPORTANT)
      /*  dashboardPage.resetFilters();
*/
        // 4️⃣ Apply Audit Status filter
        dashboardPage.selectAuditStatus("Disabled");
        dashboardPage.clickFilterButton();

        // 5️⃣ Verify results
        Assert.assertTrue(
                dashboardPage.isSearchResultDisplayed(),
                "No results after Audit Status filter"
        );

        System.out.println("Audit Status filter test passed");
    }
}
