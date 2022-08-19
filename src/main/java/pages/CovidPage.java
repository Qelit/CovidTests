package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AntibodiesPage;
import qr.QRCodeReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CovidPage {

    private WebDriver driver;
    private final By qr = By.xpath("//img[@class='qr-image']"); // qr код
    private final By emptyQr = By.xpath("//div[@class='text-center text-left-lg']"); // надпись рядом с qr
    private final By admDates = By.xpath("//div[@class='mt-8 text-plain gray small-text text-center text-left-lg']");
    private final By antibodiesTab = By.xpath("//span[text()='Антитела']");

    public CovidPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Получение ссылки из qr кода")
    public StatusPage getQRUrl(WebDriver driver){
        WebElement imageQr = driver.findElement(qr);
        String urlQr = imageQr.getAttribute("src");
        urlQr = urlQr.substring(22);
        QRCodeReader qrCodeReader = new QRCodeReader();
        String qrUrl = qrCodeReader.readQRCode(urlQr);
        driver.get(qrUrl);
        return new StatusPage(driver);
    }

    @Step("Переход на вкладку Антитела")
    public AntibodiesPage getAntibodiesPage(WebDriver driver){
        driver.findElement(antibodiesTab).click();
        return new AntibodiesPage(driver);
    }

    @Step("Проверка отсутствия qr кода на странице")
    public CovidPage getEmptyQr(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyQr));
        driver.findElement(emptyQr).isDisplayed();
        String emQr = driver.findElement(emptyQr).getText();
        Assert.assertTrue(emQr.contains("QR-код отсутствует"));
        return new CovidPage(driver);
    }

    @Step
    public String checkDateOverdueCert(WebDriver driver){
        driver.findElement(emptyQr).isDisplayed();
        String emQr = driver.findElement(emptyQr).getText();
        int index = emQr.lastIndexOf(" ");
        emQr = emQr.substring((index+1));
        return emQr;
    }

    @Step("Проверка даты окончания сертификата")
    public Date checkDateForVaccine(WebDriver driver, Date vacDate) throws ParseException {
        driver.findElement(emptyQr).isDisplayed();
        String emQr = driver.findElement(emptyQr).getText();
        int index = emQr.lastIndexOf(" ");
        emQr = emQr.substring((index+1));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        Date covidDate = format.parse(emQr);
        Date date = format.parse(format.format(vacDate));
        Assert.assertTrue(covidDate.equals(addYear(date)));
        return date;
    }

    @Step("Проверка даты окончания сертификата по антителам")
    public Date checkDateForCertAntibodies(WebDriver driver, Date vacDate) throws ParseException {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(emptyQr)));
        driver.findElement(emptyQr).isDisplayed();
        String emQr = driver.findElement(emptyQr).getText();
        int index = emQr.lastIndexOf(" ");
        emQr = emQr.substring((index+1));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        Date covidDate = format.parse(emQr);
        Date date = format.parse(format.format(vacDate));
        Assert.assertTrue(covidDate.equals(addSixMoth(date)));
        return date;
    }

    @Step("Проверка даты начала медотвода")
    public Date checkDateForAdmission(WebDriver driver, Date admDate) throws ParseException {
        driver.findElement(admDates).isDisplayed();
        String emQr = driver.findElement(admDates).getText();
        int index = emQr.lastIndexOf("с");
        int to = emQr.lastIndexOf("до");
        emQr = emQr.substring(index + 1, to - 1);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        Date covidDate = format.parse(emQr);
        Date date = format.parse(format.format(admDate));
        Assert.assertTrue(covidDate.equals(date));
        return date;
    }

    private static Date addYear(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    private static Date addSixMoth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 6);
        return cal.getTime();
    }
}
