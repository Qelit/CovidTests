package ui.pages;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CovidPage {

    private WebDriver driver;
    private final By qr = By.xpath("//img[@class='qr-image']");

    public CovidPage(WebDriver driver){
        this.driver = driver;
    }

    public void openQr(WebDriver driver) throws IOException, NotFoundException {

    }
}
