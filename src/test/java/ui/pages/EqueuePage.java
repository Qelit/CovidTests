package ui.pages;

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
    public void startAppointment(WebDriver driver){
        this.driver = driver;
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(toBegin));
        driver.findElement(toBegin).click();
    }

    @Step("Выбрать себя")
    public void pickMySelf(WebDriver driver){
        this.driver = driver;
        driver.findElement(mySelf).click();
    }

    @Step("Проверка открытия страницы выбора специальности врачей")
    public void checkOpen(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(chooseDoctorsSpeciality)));
        driver.findElement(chooseDoctorsSpeciality).isDisplayed();
    }
}
