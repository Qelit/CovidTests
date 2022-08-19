package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MfcInformationPage {
    private WebDriver driver;
    private final By statusPerson = By.xpath("//div[@class='title mb-24 col-md-4 col-lg-9']");
    private final String activeStatus = "Данные о пользователе найдены";
    private final String falseStatus = "По введенным данным сведения о сертификате не найдены";

    public MfcInformationPage(WebDriver driver) {this.driver = driver;}

    public MfcInformationPage getTrueStatusPerson(WebDriver driver){
        String stat = driver.findElement(statusPerson).getText();
        Assert.assertEquals(activeStatus, stat);
        return new MfcInformationPage(driver);
    }

    public MfcInformationPage getFalseStatusPerson(WebDriver driver){
        String stat = driver.findElement(statusPerson).getText();
        Assert.assertEquals(stat, falseStatus);
        return new MfcInformationPage(driver);
    }
}
