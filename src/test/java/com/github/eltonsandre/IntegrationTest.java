package com.github.eltonsandre;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Tag("integrationTest")
@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class IntegrationTest {

    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    protected final MutableCapabilities capabilities;

    protected WebDriver getDriver() {
        return driver.get();
    }

    public static Stream<MutableCapabilities> browserCapabilities() {
        return Stream.of(new ChromeOptions(), new FirefoxOptions());
    }

    public static URL SELENIUM_HUB_HOST;

    static {
        final String seleniumHubHost = System.getenv("SELENIUM_HUB_HOST");
        final String seleniumHubUrl = (seleniumHubHost == null ? "http://localhost:4444" : seleniumHubHost);
        try {
            SELENIUM_HUB_HOST = new URL(seleniumHubUrl.concat("/wd/hub"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("SELENIUM_HUB_HOST: " + SELENIUM_HUB_HOST);
    }


    public IntegrationTest() {
        final Map<String, MutableCapabilities> capabilitiesMap = new HashMap<>();
        capabilitiesMap.put("chrome", new ChromeOptions());
        capabilitiesMap.put("firefox", new FirefoxOptions());
        this.capabilities = new MutableCapabilities(capabilitiesMap);
    }

    @BeforeEach
    void init(final TestInfo testInfo) {
        RemoteWebDriver webDriver = new RemoteWebDriver(SELENIUM_HUB_HOST, new ChromeOptions());

        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();

        driver.set(webDriver);
    }

    @AfterEach
    void tearDown() {
        driver.get().executeScript("window.localStorage.clear()");
        getDriver().quit();
    }

    @AfterAll
    static void remove() {
        driver.remove();
    }

}
