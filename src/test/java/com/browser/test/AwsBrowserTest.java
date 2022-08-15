package com.browser.test;

import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

import software.amazon.awssdk.arns.ArnResource.DefaultBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class AwsBrowserTest {

    private static String BROWSER_CHROME = "chrome";
    private static String BROWSER_FIREFOX = "firefox";


    private static RemoteWebDriver driver;
    DesiredCapabilities desired_capabilities = new DesiredCapabilities();

    Map<String, String> data = new HashMap<String, String>();


    @BeforeTest
    @Parameters({"browser"})
    public void setUp(String browser) throws Exception {
        URL testGridUrl = null;


//        System.setProperty("aws.accessKeyId", getProperties().get("aws_access_key"));
//        System.setProperty("aws.secretAccessKey", getProperties().get("aws_secret_access_key"));

        DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2).build();
        CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
                .expiresInSeconds(300)
                .projectArn(getProperties().get("project_arn"))
                .build();

        try {
            CreateTestGridUrlResponse response = client.createTestGridUrl(request);
            testGridUrl = new URL(response.url());
            Assert.assertNotNull(testGridUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCapabilities(browser);
        driver = new RemoteWebDriver(testGridUrl, desired_capabilities);
    }

    @Test
    public void searchGoogle() {

        driver.get("https://www.google.com/");
        WebElement inputSearch = driver.findElement(By.xpath("//input[@name='q']"));
        inputSearch.sendKeys("AWS Device Farm");
        inputSearch.sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='result-stats']")));
        WebElement results = driver.findElement(By.xpath("//div[@id='result-stats']"));
        Assert.assertTrue(results.isDisplayed());
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    private Map<String, String> getProperties() throws Exception {

        InputStream inputStream = new FileInputStream(new File("src\\test\\resources\\action.yaml"));

        Yaml yaml = new Yaml();
        data = yaml.load(inputStream);


        return data;

    }

    private void getCapabilities(String browser) {
        System.out.println("****************************************");
        System.out.println("Browser: " + browser);
        System.out.println("****************************************");

        if (browser.equalsIgnoreCase(BROWSER_CHROME)) {
            desired_capabilities.setCapability("browserName",BROWSER_CHROME);
        } else if (browser.equalsIgnoreCase(BROWSER_FIREFOX)) {
            desired_capabilities.setCapability("browserName",BROWSER_FIREFOX);

        }
    }
}
