package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

public class GetMedicalAttachment {
    private WebDriver driver;
    private final By start = By.xpath("//button[@class='button font-']");//кнопка "Начать



    public GetMedicalAttachment(WebDriver driver){
        this.driver = driver;
    }


    //Нажимаем кнопку "Начать" на странице услуги
    @Step("Нажать Начать")
    public GetMedicalAttachment startService(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(start));
        driver.findElement(start).click();
        return new GetMedicalAttachment(driver);
    }

}
