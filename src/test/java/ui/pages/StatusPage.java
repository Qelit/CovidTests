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
    private final By status = By.xpath("//span[@id='status']"); //поле со статусом
    private final By greenBg = By.xpath("//div[@id='green-bg']"); //зеленая плашка
    private final By redBg = By.xpath("//div[@id='red-bg']"); //красная плашка
    private final By admissionContainer = By.xpath("//div[@id='admission-container']");
    private final By admissionStatus = By.xpath("//span[@id='admission-status']");
    private final String activeUntil = "Действителен до";
    private final String expired = "Срок истёк";
    private final String activeSince = "Действителен с";
    private final String empty = "Отсутствует";
    private final String admissionUntil = "Действует до";
    private final String admissionInfinity = "Действует бессрочно";

    public StatusPage(WebDriver driver) { this.driver = driver;}

    @Step("Проверка активного статуса сертификата")
    public void getActiveStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(greenBg, activeUntil);
    }

    @Step("Проверка активного статуса сертификата и даты окончания сертификата")
    public void getActiveStatus(WebDriver driver, Date vacDate) throws ParseException {
        this.driver = driver;
        String statusText = checkStatus(greenBg, activeUntil);
        int index = statusText.lastIndexOf(" ");
        statusText = statusText.substring((index+1));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date statusDate = format.parse(statusText);
        vacDate = format.parse(format.format(vacDate));
        Assert.assertEquals(addYear(vacDate), statusDate);
    }

    @Step("Проверка активного статуса сертификата и даты окончания сертификата")
    public void getActiveStatusAntibodies(WebDriver driver, Date vacDate) throws ParseException {
        this.driver = driver;
        String statusText = checkStatus(greenBg, activeUntil);
        int index = statusText.lastIndexOf(" ");
        statusText = statusText.substring((index+1));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date statusDate = format.parse(statusText);
        vacDate = format.parse(format.format(vacDate));
        Assert.assertEquals(addSixMonthes(vacDate), statusDate);
    }

    @Step("Проверка истечения срока сертификата")
    public void getOverdueStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(redBg, expired);
    }

    @Step("Проверка истечения срока сертификата и даты истечения сертификата")
    public void getOverdueStatus(WebDriver driver, String date, Date vacDate) throws ParseException {
        this.driver = driver;
        String statusText = checkStatus(redBg, expired);
        Date startDate = checkDateForVaccine(statusText,date);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vacDateNoTime = formatter.parse(formatter.format(vacDate));
        Assert.assertTrue(startDate.equals(addYear(vacDateNoTime)));
    }

    @Step("Проверка валидации медотвода до срока")
    public void getAdmissionStatusUntil(WebDriver driver){
        this.driver = driver;
        checkAdmissionStatus(admissionContainer, admissionUntil);
    }

    @Step("Проверка валидации медотвода до срока и проверка срока")
    public void getAdmissionStatusUntil(WebDriver driver, Date date) throws ParseException {
        this.driver = driver;
        String statusText = checkAdmissionStatus(admissionContainer, admissionUntil);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date endDate = checkDateForVaccine(statusText, formatter.format(addYear(date)));
        Date vacDateNoTime = formatter.parse(formatter.format(date));
        Assert.assertTrue(endDate.equals(addYear(vacDateNoTime)));
    }

    @Step("Проверка валидации бессрочного медотвода")
    public void getAdmissionStatusInfinity(WebDriver driver){
        this.driver = driver;
        checkAdmissionStatus(admissionContainer, admissionInfinity);
    }

    @Step("Проверка того, что срок сертификата еще не наступил")
    public void getNotArrivedStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(redBg, activeSince);
    }

    @Step("Проверка отсутствующего сертификата")
    public void getEmptyStatus(WebDriver driver){
        this.driver = driver;
        checkStatus(redBg, empty);
    }

    @Step("Проверка статуса")
    private String checkStatus(By colorBg, String checkText){
        driver.findElement(colorBg).isDisplayed();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(status, checkText));
        String statusText = driver.findElement(status).getText();
        Assert.assertTrue(statusText.contains(checkText));
        return statusText;
    }

    @Step("Проверка статуса медотвода")
    private String checkAdmissionStatus(By container, String checkText){
        driver.findElement(container);
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(admissionStatus, checkText));
        String statusText = driver.findElement(admissionStatus).getText();
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

    private static Date addYear(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    private static Date addSixMonthes(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 6);
        return cal.getTime();
    }
}
