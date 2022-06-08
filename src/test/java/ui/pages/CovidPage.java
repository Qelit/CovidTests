package ui.pages;

import gherkin.deps.net.iharder.Base64;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import qr.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CovidPage {

    private WebDriver driver;
    private final By qr = By.xpath("//img[@class='qr-image']"); // qr код
    private final By emptyQr = By.xpath("//div[@class='text-center text-left-lg']"); // надпись рядом с qr

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

    @Step("Проверка отсутствия qr кода на странице")
    public void getEmptyQr(WebDriver driver){
        this.driver = driver;
        driver.findElement(emptyQr).isDisplayed();
        String emQr = driver.findElement(emptyQr).getText();
        Assert.assertTrue(emQr.contains("QR-код отсутствует"));
    }

    @Step
    public String checkDateOverdueCert(WebDriver driver){
        this.driver = driver;
        driver.findElement(emptyQr).isDisplayed();
        String emQr = driver.findElement(emptyQr).getText();
        int index = emQr.lastIndexOf(" ");
        emQr = emQr.substring((index+1));
        return emQr;
    }

    @Step("Проверка даты окончания сертификата")
    public Date checkDateForVaccine(WebDriver driver, Date vacDate) throws ParseException {
        this.driver = driver;
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

    public static Date addYear(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }
}
