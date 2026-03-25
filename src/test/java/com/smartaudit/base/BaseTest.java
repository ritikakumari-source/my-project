package com.smartaudit.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 🔑 VERY IMPORTANT: clear session
        driver.manage().deleteAllCookies();

        // open app fresh
        driver.get("https://vauditdemo.myndact.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            /*driver.quit();*/
        }
    }
}
