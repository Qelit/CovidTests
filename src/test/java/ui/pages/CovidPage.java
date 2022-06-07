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

public class CovidPage {

    private WebDriver driver;
    private final By qr = By.xpath("//img[@class='qr-image']");
    private final By emptyQr = By.xpath("//div[@class='text-center text-left-lg']");

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
}
