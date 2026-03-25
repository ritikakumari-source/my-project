package com.smartaudit;

import com.smartaudit.base.BaseTest;
import com.smartaudit.pages.LoginPage;
import com.smartaudit.pages.SessionLimitPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void loginSessionValidation() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("ritika12", "ritika123");

        SessionLimitPage sessionPage = new SessionLimitPage(driver);

        // CASE 2: Session limit hit
        if (sessionPage.isSessionPageDisplayed()) {

            System.out.println("⚠️ Session limit page displayed");

            sessionPage.logoutExistingSession();

            // ✅ EXPECTED: redirect to login page
            Assert.assertTrue(
                    loginPage.isLoginPageDisplayed(),
                    "User was NOT redirected to login page after session handling"
            );

            System.out.println("✅ Session handled and user redirected to login page");

            return; // 🔴 STOP here, do not go to dashboard
        }

        // CASE 1: New user → normal login
        System.out.println("✅ New user login, no session conflict");
    }
}
