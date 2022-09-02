package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

public class CallingDoctorAtHome {
    private WebDriver driver;
    private final By start = By.xpath("//button[@class='button font-']");//кнопка "Начать
    private final By clickOnMyself = By.xpath("//span[@class = 'answer-btn__title'][text()='Мне']");//кнопка "Мне"
    private final By correctData = By.xpath("//button[@class='button font-']");//кнопка верно на экране подтверждения
    private final By insurancePolicy = By.xpath("//button[@class='button font-']");//кнопка верно на экране полиса
    private final By phone = By.xpath("//button[@class='button font-']");//кнопка верно на телефоне
    private final By changeAddress = By.xpath("//button[@class='white button font-']");//кнопка указать другой адрес
    private final By deleteAddress = By.xpath("//input[@type='text']");//кнопка указать другой адрес
    private final By clarifyAddress = By.xpath("//div[@class='head']");//для клика по другой области
    private final By confirmAddress = By.xpath("//button[@class='button font-']");//кнопка верно на адресе
    private final By continueConfirmButton = By.xpath("//button[@class='button font-']");//кнопка продолжить на доп.инфе
    private final By textFieldSymptoms = By.xpath("//div[@class='multiline-input']");//текстовое поле на симптомах
    private final By continueSymptomsButton = By.xpath("//button[@class='button font-']");//кнопка продолжить на доп.инфе
    private final By confirmTimeButton = By.xpath("//button[@class='button font-']");//кнопка подтвердить вызов


    private String address = "468321, г. Байконур, ул. Ленина, д. 10, кв. 15";

    public CallingDoctorAtHome (WebDriver driver){
        this.driver = driver;
    }

    //Нажимаем кнопку "Начать" на странице услуги
    @Step("Нажать Начать")
    public CallingDoctorAtHome startService(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(start));
        driver.findElement(start).click();
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

    //Нажимаем кнопку "Верно" на экране "Подтвердите полис ОМС"
    @Step("Нажать Верно")
    public CallingDoctorAtHome confirmPolicy(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(insurancePolicy));
        driver.findElement(insurancePolicy).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажимаем кнопку "Верно" на экране "Подтвердите ваш контактный телефон"
    @Step("Нажать верно")
    public CallingDoctorAtHome confirmPhone(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(phone));
        driver.findElement(phone).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажимаем кнопку "Указать другой адрес" на экране "Куда приехать врачу?"
    @Step("Нажать Указать другой адрес")
    public CallingDoctorAtHome clickChangeAddress(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(changeAddress));
        driver.findElement(changeAddress).click();
        return new CallingDoctorAtHome(driver);
    }
    //Нажимаем на строку для ввода с адресом и стираем старый адрес, добавляем новый и нажимаем Верно
    @Step("Ввести другой адрес")
    public CallingDoctorAtHome deleteOldAddress(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(deleteAddress));
        driver.findElement(deleteAddress).click();
        driver.findElement(deleteAddress).sendKeys(Keys.CONTROL + "a");
        driver.findElement(deleteAddress).sendKeys(Keys.DELETE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(deleteAddress).sendKeys(Keys.CONTROL + "a");
        driver.findElement(deleteAddress).sendKeys(Keys.DELETE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(deleteAddress).sendKeys(address);
        driver.findElement(clarifyAddress).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmAddress));
        driver.findElement(confirmAddress).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажать "Продолжить" на экране "Дополнительные сведения для врача"
    @Step("Нажать Продолжить")
    public CallingDoctorAtHome continueConfirm(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(continueConfirmButton));
        driver.findElement(continueConfirmButton).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажать на поле для ввода, ввести текст и нажать "Продолжить" на экране "Укажите симптомы"
    @Step("Ввести текст в поле симптомов и нажать Продолжить")
    public CallingDoctorAtHome continueSymptoms(WebDriver driver){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(textFieldSymptoms));
        driver.findElement(textFieldSymptoms).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(textFieldSymptoms).sendKeys("боль");
       wait.until(ExpectedConditions.elementToBeClickable(continueSymptomsButton));
       driver.findElement(continueSymptomsButton).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажать "Подтвердить вызов" на экране "Время визита врача"
    @Step("Нажать Подтвердить вызов")
    public CallingDoctorAtHome confirmTime (WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 30, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmTimeButton));
        driver.findElement(confirmTimeButton).click();
        return new CallingDoctorAtHome(driver);
    }
}
