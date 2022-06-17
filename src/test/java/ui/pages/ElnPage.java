package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ElnPage {
    private WebDriver driver;
    private final By forNumberEln = By.xpath("//input[@id='app-radio-1']");
    private final By forPeriod = By.xpath("//input[@id='app-radio-2']");
    private final By elnNumber = By.xpath("//input[@name='numberEln']");
    private final By elnPeriod = By.xpath("//lib-dropdown[@name='dateEln']");
    private final By month1 = By.xpath("//div[@itemid='1month']");
    private final By monthes3 = By.xpath("//div[@itemid='3months']");
    private final By monthes6 = By.xpath("//div[@itemid='6months']");
    private final By selectPeriod = By.xpath("//div[@itemid='custom']");
    private final By buttonGetInfo = By.xpath("//button[@type='button']");
    private final By dateFromPeriod = By.xpath("//input[@name = 'dateFrom']");
    private final By dateToPeriod = By.xpath("//input[@name = 'dateTo']");

    public ElnPage(WebDriver driver) {this.driver = driver;}

    @Step("Получить ЭЛН по номеру")
    public ElnDetailsPage getElnForNumber(String number){
        driver.findElement(elnNumber).sendKeys(number);
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(buttonGetInfo));
        driver.findElement(buttonGetInfo).click();
        return new ElnDetailsPage(driver);
    }

    @Step("Получить список элн за 6 месяцев")
    public ElnResultPage getElnFor6Monthes(WebDriver driver){
        return getElnForPeriod(monthes6);
    }

    @Step("Получить список элн за 3 месяца")
    public ElnResultPage getElnFor3Monthes(WebDriver driver){
        return getElnForPeriod(monthes3);
    }

    @Step("Получить список элн за 1 месяц")
    public ElnResultPage getElnFor1Month(WebDriver driver){
        return getElnForPeriod(month1);
    }

    @Step("Выбор периода")
    private ElnResultPage getElnForPeriod(By month){
        driver.findElement(elnPeriod).click();
        driver.findElement(month).click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(buttonGetInfo));
        driver.findElement(buttonGetInfo).click();
        return new ElnResultPage(driver);
    }

    public ElnResultPage getElnForNewPeriod(WebDriver driver){
        Date dateFrom = new Date();
        Date dateTo = new Date();
        return getElnForSelectPeriod(dateFrom, dateTo);
    }

    private ElnResultPage getElnForSelectPeriod(Date dateFrom, Date dateTo){
        driver.findElement(elnPeriod).click();
        driver.findElement(selectPeriod).click();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < 8; i++) {
            driver.findElement(dateFromPeriod).sendKeys(Keys.BACK_SPACE);
        }
        driver.findElement(dateFromPeriod).sendKeys(formatter.format(dateFrom));
        driver.findElement(forPeriod).click();
        for (int i = 0; i < 8; i++) {
            driver.findElement(dateToPeriod).sendKeys(Keys.BACK_SPACE);
        }
        driver.findElement(dateToPeriod).sendKeys(formatter.format(dateTo));
        driver.findElement(forPeriod).click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(buttonGetInfo));
        driver.findElement(buttonGetInfo).click();
        return new ElnResultPage(driver);
    }
}
