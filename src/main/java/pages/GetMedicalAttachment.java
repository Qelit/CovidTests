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
    private final By myself = By.xpath("//span[@class='answer-btn__title'][text()='Мне']");//кнопка "Мне"
    private final By goToTheService = By.xpath("//button[@class='button font-']");//кнопка "Перейти к заявлению"
    private final By checkData = By.xpath("//button[@class='button font-']");//кнопка "Верно" на экране данных УЗ
    private final By confirmYourPolicy = By.xpath("//button[@class='button font-']");//кнопка "Верно" на экране полиса
    private final By confirmYourPhone = By.xpath("//button[@class='button font-']");//кнопка "Верно" на экране телефона
    private final By confirmYourEmail = By.xpath("//button[@class='button font-']");//кнопка "Верно" на экране почты
    private final By enterDate = By.xpath("//input[@name='regDate']");//поле для ввода даты
    private final By confirmYourRegistrationAddress = By.xpath("//button[@class='button font-']");//кнопка "Верно" на экране почты
    private final By noForAddress = By.xpath("//span[@class='answer-btn__title'][text()='Нет']");//кнопка "Нет"
    private final By deleteTheAddress = By.xpath("//input[@type='text']");//поле для ввода адреса
    private final By clarifyTheAddress = By.xpath("//p[@class='mt-16']");//для клика по другой области
    private final By confirmTheAddress = By.xpath("//button[@class='button font-']");//кнопка продолжить на адресе
    private final By confirmAnotherAddressButton = By.xpath("//button[@class='white button font- ng-star-inserted']");//кнопка продолжить на экране в прикреплении могут отказать
    private final By changeAttachmentButton = By.xpath("//button[@class='button font- ng-star-inserted']");//кнопка "Сменить прикрепление"


    private String address = "308001, обл. Белгородская, г. Белгород, пр-кт. Белгородский, д. 11, кв. 1";

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
    public GetMedicalAttachment forMyself(WebDriver driver) {
        driver.findElement(myself).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем "Перейти к заявлению" на экране "Для подачи заявления потребуются"
    @Step("Нажать Перейти к заявлению")
    public GetMedicalAttachment goService(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(goToTheService));
        driver.findElement(goToTheService).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем "Верно" на экране "Проверьте ваши данные"
    @Step("Нажать Верно")
    public GetMedicalAttachment checkYourData(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(checkData));
        driver.findElement(checkData).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем "Верно" на экране "Подтвердите полис ОМС"
    @Step("Нажать Верно")
    public GetMedicalAttachment confirmPolicy(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmYourPolicy));
        driver.findElement(confirmYourPolicy).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем "Верно" на экране "Подтвердите контактный телефон"
    @Step("Нажать Верно")
    public GetMedicalAttachment confirmPhone(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmYourPhone));
        driver.findElement(confirmYourPhone).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем "Верно" на экране "Подтвердите адрес электронной почты"
    @Step("Нажать Верно")
    public GetMedicalAttachment confirmEmail(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmYourEmail));
        driver.findElement(confirmYourEmail).click();
        return new GetMedicalAttachment(driver);
    }

    //Вводим дату и нажимаем "Верно" на экране "Подтвердите адрес регистрации"
    @Step("Нажать Верно")
    public GetMedicalAttachment confirmRegistration(WebDriver driver) {
        driver.findElement(enterDate).click();
        driver.findElement(enterDate).sendKeys("21012020");
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmYourRegistrationAddress));
        driver.findElement(confirmYourRegistrationAddress).click();
        return new GetMedicalAttachment(driver);
    }

    //Вводим дату и нажимаем "Нет" на экране "Подтвердите адрес регистрации"
    @Step("Нажать Нет")
    public GetMedicalAttachment declineAddress(WebDriver driver) {
        driver.findElement(noForAddress).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем на строку для ввода с адресом и стираем старый адрес, добавляем новый и нажимаем "Продолжить"
    @Step("Ввести другой адрес")
    public CallingDoctorAtHome deleteAddress(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(deleteTheAddress));
        driver.findElement(deleteTheAddress).click();
        driver.findElement(deleteTheAddress).sendKeys(Keys.CONTROL + "a");
        driver.findElement(deleteTheAddress).sendKeys(Keys.DELETE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(deleteTheAddress).sendKeys(Keys.CONTROL + "a");
        driver.findElement(deleteTheAddress).sendKeys(Keys.DELETE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(deleteTheAddress).sendKeys(address);
        driver.findElement(clarifyTheAddress).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmTheAddress));
        driver.findElement(confirmTheAddress).click();
        return new CallingDoctorAtHome(driver);
    }

    //Нажимаем "Продолжить" на экране "В прикреплении могут отказать"
    @Step("Нажать Продолжить")
    public GetMedicalAttachment confirmAnotherAddress(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmAnotherAddressButton));
        driver.findElement(confirmAnotherAddressButton).click();
        return new GetMedicalAttachment(driver);
    }

    //Нажимаем "Продолжить" на экране "Поликлиника, к которой вы прикреплены"
    @Step("Нажать Сменить прикрепление")
    public GetMedicalAttachment changeAttachment(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 15, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(changeAttachmentButton));
        driver.findElement(changeAttachmentButton).click();
        return new GetMedicalAttachment(driver);
    }
}