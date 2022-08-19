package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EqueuePage {
    WebDriver driver;
    private final By toBegin = By.xpath("//button[@type='button']");
    private final By mySelf = By.xpath("//a[text()=' Себя ']");
    private final By child = By.xpath("//a[text()=' Ребёнка ']");
    private final By anotherPerson = By.xpath("//a[text()=' Другого человека ']");
    private final By chooseDoctorsSpeciality = By.xpath("//h3[text()='Выберите специальность врача']");

    public EqueuePage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Нажать Начать")
    public EqueuePage startAppointment(){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(toBegin));
        driver.findElement(toBegin).click();
        return new EqueuePage(driver);
    }

    @Step("Выбрать себя")
    public EqueuePage pickMySelf(){
        driver.findElement(mySelf).click();
        return new EqueuePage(driver);
    }

    @Step("Проверка открытия страницы выбора специальности врачей")
    public EqueuePage checkOpen(){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(chooseDoctorsSpeciality)));
        driver.findElement(chooseDoctorsSpeciality).isDisplayed();
        return new EqueuePage(driver);
    }
}
