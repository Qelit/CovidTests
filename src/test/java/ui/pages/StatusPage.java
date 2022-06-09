package ui.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatusPage {

    private WebDriver driver;
    private By status = By.xpath("//span[@id='status']"); //поле со статусом
    private By greenBg = By.xpath("//div[@id='green-bg']"); //зеленая плашка
    private By redBg = By.xpath("//div[@id='red-bg']"); //красная плашка
    private By admissionContainer = By.xpath("//div[@id='admission-container']");
    private By admissionStatus = By.xpath("//span[@id='admission-status']");
    private String activeUntil = "Действителен до";
    private String expired = "Срок истёк";
    private String activeSince = "Действителен с";
    private String empty = "Отсутствует";
    private String admissionUntil = "Действует до";

    public StatusPage(WebDriver driver) { this.driver = driver;}

    @Step("Проверка активного статуса сертификата")
    public void getActiveStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(greenBg, activeUntil);
    }

    @Step("Проверка истечения срока сертификата")
    public void getNegativeStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(redBg, expired);
    }

    @Step("Проверка истечения срока сертификата и даты истечения сертификата")
    public void getNegativeStatus(WebDriver driver, String date, Date vacDate) throws ParseException {
        this.driver = driver;
        String statusText = checkStatus(redBg, expired);
        Date startDate = checkDateForVaccine(statusText,date);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vacDateNoTime = formatter.parse(formatter.format(vacDate));
        Assert.assertTrue(startDate.equals(addYear(vacDateNoTime)));
    }

    @Step("Проверка валидации медотвода")
    public void getAdmissionStatus(WebDriver driver){
        this.driver = driver;
        driver.findElement(admissionContainer);
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(admissionStatus, admissionUntil));
        String statusText = driver.findElement(admissionStatus).getText();
        Assert.assertTrue(statusText.contains(admissionUntil));
    }

    @Step("Проверка того, что срок сертификата еще не наступил")
    public void getHasNotArriveStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(redBg, activeSince);
    }

    @Step("Проверка отсутствующего сертификата")
    public void getEmptyStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(redBg, empty);
    }

    @Step("Проверка статуса")
    public String checkStatus(By colorBg, String checkText){
        driver.findElement(colorBg).isDisplayed();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(status, checkText));
        String statusText = driver.findElement(status).getText();
        Assert.assertTrue(statusText.contains(checkText));
        return statusText;
    }

    @Step("Проверка даты действия сертификата на экране валидации")
    //преобразовать в дату
    public Date checkDateForVaccine(String statusText, String date) throws ParseException {
        int index = statusText.lastIndexOf(" ");
        statusText = statusText.substring((index+1));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date statusDate = format.parse(statusText);
        format.applyPattern("dd.MM.yy");
        Date covidDate = format.parse(date);
        Assert.assertTrue(statusDate.equals(covidDate));
        return covidDate;
    }

    public static Date addYear(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }
}
