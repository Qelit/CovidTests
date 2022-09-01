package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CallingDoctorAtHome {
    private WebDriver driver;
    private final By Start = By.xpath("//button[@class='button font-']");//кнопка "Начать
    private final By clickOnMyself = By.xpath("//button[@class='btn--white btn--shadow']");//кнопка "Мне"
    private final By correctData = By.xpath("//button[@class='button font-']");//кнопка верно

    public CallingDoctorAtHome (WebDriver driver){
        this.driver = driver;
    }

    //Нажимаем кнопку "Начать" на странице услуги
    @Step("Нажать Начать")
    public CallingDoctorAtHome startService(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(Start));
        driver.findElement(Start).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажимаем кнопку "Мне" на экране-квизе "Кому нужен врач?"
    @Step("Выбрать Мне")
    public void Myself(WebDriver driver){
        driver.findElement(clickOnMyself).click();
    }

    //Нажимаем кнопку "Верно" на экране "Проверьте корректность ваших данных"
    @Step("Нажать Верно")
    public CallingDoctorAtHome Correct(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(correctData));
        driver.findElement(correctData).click();
        return new CallingDoctorAtHome(driver);
    }
}
