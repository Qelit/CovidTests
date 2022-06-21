package ui.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AntibodiesPage {
    WebDriver driver;
    private final By antibodiesTab = By.xpath("//span[text()='Антитела']");
    private final By certTab = By.xpath("//span[text()='Сертификат']");
    private final By impossible = By.xpath("//h5[@class='title-h5']");
    private final By buttonGetCert = By.xpath("//span[text()='Сформировать сертификат']/parent::span/parent::button");
    private final By buttonGetCertForResult = By.xpath("//span[text()='Сформировать сертификат']/parent::button");
    private final By cancel= By.xpath("//span[text()='Отменить']/parent::button");
    private final String noForm = "Невозможно сформировать QR-код по результатам теста";

    public AntibodiesPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Формирование сертификата")
    public void formCert(WebDriver driver){
        this.driver = driver;
        driver.findElement(buttonGetCert).click();
        driver.findElement(buttonGetCertForResult).click();
    }

    public void checkNotButtonFormCert(WebDriver driver){
        this.driver = driver;
        String imp = driver.findElement(impossible).getText();
        Assert.assertEquals(imp, noForm);
    }

    @Step("Открыть вкладку сертификат")
    public CovidPage openCovidPage(WebDriver driver){
        this.driver = driver;
        driver.findElement(certTab).click();
        return new CovidPage(driver);
    }
}
