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
    private final By clickOnMyself = By.xpath("//span[@class = 'answer-btn__title'][text()='Мне']");//кнопка "Мне"
    private final By goToTheService = By.xpath("//button[@class='button font-']");//кнопка "Перейти к заявлению"



    public GetMedicalAttachment(WebDriver driver) {
        this.driver = driver;
    }


    //Нажимаем кнопку "Начать" на странице услуги
    @Step("Нажать Начать")
    public GetMedicalAttachment startAttachment(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(start));
        driver.findElement(start).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем кнопку "Мне" на экране-квизе "Кому требуется прикрепление?"
    @Step("Выбрать Мне")
    public void forMyself(WebDriver driver) {
        driver.findElement(clickOnMyself).click();
    }

    //Нажимаем "Перейти к заявлению" на экране "Для подачи заявления потребуются"
    @Step("Нажать Перейти к заявлению")
    public GetMedicalAttachment goService(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(goToTheService));
        driver.findElement(goToTheService).click();
        return new GetMedicalAttachment(driver);
    }
}
