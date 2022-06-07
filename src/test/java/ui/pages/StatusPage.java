package ui.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StatusPage {

    private WebDriver driver;
    private By status = By.xpath("//span[@id='status']");
    private By greenBg = By.xpath("//div[@id='green-bg']");
    private By redBg = By.xpath("//div[@id='red-bg']");

    public StatusPage(WebDriver driver) { this.driver = driver;}

    @Step("Проверка активного статуса сертификата")
    public void getActiveStatus(){
        driver.findElement(greenBg).isDisplayed();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(status, "Действителен до"));
        String statusText = driver.findElement(status).getText();
        Assert.assertTrue(statusText.contains("Действителен до"));
    }

    @Step("Проверка истечения срока сертификата")
    public void getNegativeStatus(){
        driver.findElement(redBg).isDisplayed();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(status, "Срок истёк"));
        String statusText = driver.findElement(status).getText();
        Assert.assertTrue(statusText.contains("Срок истёк"));
    }

    @Step("Проверка того, что срок сертификата еще не наступил")
    public void getHasNotArriveStatus(){
        driver.findElement(redBg).isDisplayed();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(status, "Действителен с"));
        String statusText = driver.findElement(status).getText();
        Assert.assertTrue(statusText.contains("Действителен с"));
    }
}
