package com.github.eltonsandre;

import com.github.eltonsandre.util.ScreenshotUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.junit.jupiter.api.Tag;

@DisplayName("Test exemplo google")
class GoogleSearchTest extends IntegrationTest {

    @FindBy(how = How.CSS, using = "//input[@value='Google Search']")
    private WebElement searchButton;

    @FindBy(how = How.CSS, using = "//input[title=Pesquisar]")
    private WebElement searchText;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Images')]")
    private WebElement imageSearch;

    @Test
    @Order(1)
    void openGoogle() {
        WebDriver webDriver = getDriver();
//        PageFactory.initElements(webDriver, this);
        webDriver.navigate().to("http://www.google.com");
        ScreenshotUtils.printScreen(webDriver, "./screenshot/openGoogle.jpeg");

        Assertions.assertEquals("Google", webDriver.getTitle());
    }

    @Test
    @Order(2)
    void enterGoogleSearchAndViewResults() {
        WebDriver webDriver = getDriver();
        webDriver.navigate().to("http://www.google.com");
//        PageFactory.initElements(webDriver, this);

        WebElement searchText = webDriver.findElement(By.cssSelector("input[title=Pesquisar]"));
        searchText.sendKeys("hello world");
        ScreenshotUtils.printScreen(webDriver, "./screenshot/enterGoogleSearchAndViewResults/passo-1.jpeg");

        WebElement searchButton = webDriver.findElement(By.cssSelector("input[value='Pesquisa Google']"));
        searchButton.click();
        ScreenshotUtils.printScreen(webDriver, "./screenshot/enterGoogleSearchAndViewResults/passo-2.jpeg");

        Assertions.assertEquals("hello world - Pesquisa Google", webDriver.getTitle());
    }

    @Test
    @Order(3)
    void enterGoogleSearchAndImageSearch() throws Exception {
        WebDriver webDriver = getDriver();
        webDriver.navigate().to("http://www.google.com");

        WebElement searchText = webDriver.findElement(By.cssSelector("input[title=Pesquisar]"));
        searchText.sendKeys("hello world");
        ScreenshotUtils.printScreen(webDriver, "./screenshot/enterGoogleSearchAndImageSearch/passo-1.jpeg");

        WebElement searchButton = webDriver.findElement(By.cssSelector("input[value='Pesquisa Google']"));
        searchButton.click();
        ScreenshotUtils.printScreen(webDriver, "./screenshot/enterGoogleSearchAndImageSearch/passo-2.jpeg");

        WebElement imageSearch = webDriver.findElement(By.xpath("//a[contains(text(), 'Imagens')]"));
        imageSearch.click();
        ScreenshotUtils.printScreen(webDriver, "./screenshot/enterGoogleSearchAndImageSearch/passo-3.jpeg");
    }

}
