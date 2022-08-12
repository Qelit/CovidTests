package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AppointmentToDoctorPage {
    WebDriver driver;
    private final By getService = By.xpath("//a[text()='Получить услугу']");

    public AppointmentToDoctorPage(WebDriver driver) { this.driver = driver;}

    public EqueuePage getEqueuePage(WebDriver driver){
        driver.findElement(getService).click();
        return new EqueuePage(driver);
    }
}
