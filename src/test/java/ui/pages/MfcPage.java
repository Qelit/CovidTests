package ui.pages;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import users.User;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class MfcPage {
    private WebDriver driver;
    private User user;
    private final By lastName = By.xpath("//div[@id='lastName']//input"); //Фамилия
    private final By firstName = By.xpath("//div[@id='firstName']//input"); //Имя
    private final By middleName = By.xpath("//div[@id='middleName']//input"); //Отчество
    private final By radioButtonMale = By.xpath("//lib-radio[@value='MALE']"); //Пол - мужчина
    private final By radioButtonFemale = By.xpath("//lib-radio[@value='FEMALE']"); //Пол - женщина
    private final By birthDate = By.xpath("//div[@id='birthDate']//input"); //Дата рождения
    private final By docTypeList = By.xpath("//div[@id='docTypeList']"); //Документ удостоверяющий личность
    private final By series = By.xpath("//lib-base-masked-input[@formcontrolname='series']/div/input"); //Серия
    private final By number = By.xpath("//lib-base-masked-input[@formcontrolname='number']/div/input"); //Номер
    private final By issueDate = By.xpath("//div[@id='issueDate']//input"); //Дата выдачи
    private final By issuedBy = By.xpath("//div[@id='issuedBy']//input");  //Кем выдан
    private final By radioButtonOnForeighnPassport = By.xpath("//input[@id='app-checkbox-1']");
    private final By snils = By.xpath("//div[@id='snils']//input");
    private final By omsNumber = By.xpath("//div[@id='numberOms']");
    private final By omsSeries = By.xpath("//div[@id='seriesOms']");
    private final By certForRepresentative = By.xpath("//input[@id='app-checkbox-2']");
    private final By buttonGetCert = By.xpath("//button[@type='button']");
    private final By seriesForeighnPassport = By.xpath("//h4[text()='Загранпаспорт']/parent::div/parent::div/following-sibling::div//lib-base-masked-input[@formcontrolname='series']/div/input");
    private final By numberForeighnPassport = By.xpath("//h4[text()='Загранпаспорт']/parent::div/parent::div/following-sibling::div//lib-base-masked-input[@formcontrolname='number']/div/input");
    private final By lastNameEng = By.xpath("//div[@id='lastNameEng']");
    private final By firsthNameEng = By.xpath("//div[@id='firstNameEng']");
    private final By representativeLastName = By.xpath("//div[@id='representativeLastName']");
    private final By representativeFirstName = By.xpath("//div[@id='representativeFirstName']");
    private final By representativeMiddleName = By.xpath("//div[@id='representativeMiddleName']");
    private final By representativeDocTypeList = By.xpath("//div[@id='representativeDocTypeList']");
    private final By representativeSeries = By.xpath("//h4[text()='Представитель заявителя']/parent::div/parent::div/following-sibling::div//lib-base-masked-input[@formcontrolname='series']/div/input");
    private final By representativeNumber = By.xpath("//h4[text()='Представитель заявителя']/parent::div/parent::div/following-sibling::div//lib-base-masked-input[@formcontrolname='number']/div/input");
    private final By representativeIssueDate = By.xpath("//div[@id='representativeIssueDate']");
    private final By representativeIssuedBy = By.xpath("//div[@id='representativeIssuedBy']");
    private final By representDoc = By.xpath("//div[@id='representDoc']");
    private final By buttonOOOYuteh = By.xpath("//button[@title='ООО ЮТЕХ']");
    private final By passportRus = By.xpath("//div[@itemid='passportRus']");
    private final By passportRusEng = By.xpath("//div[@itemid='passportRusEng']");
    private final By passportEng = By.xpath("//div[@itemid='passportEng']");
    private final By birthCertificate = By.xpath("//div[@itemid='birthCertificate']");
    private final String oufms = "ОУФМС";

    public MfcPage(WebDriver driver) {this.driver = driver;}

    @Step("Получение из json файла пользователя для тестирования вакцины")
    private User getVaccineUser(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            user = mapper.readValue(new File("src/test/resources/vaccineUser.json"), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Step("Выбрать сотрудника ООО Ютех")
    public MfcPage enterEmployer(WebDriver driver){
        this.driver = driver;
        driver.findElement(buttonOOOYuteh).click();
        return this;
    }

    @Step("Получение сертификата по вакцине")
    public MfcInformationPage getActiveVaccineCert(WebDriver driver) throws ParseException {
        this.driver = driver;
        User user = getVaccineUser();
        driver.findElement(lastName).sendKeys(user.getSurName());
        driver.findElement(firstName).sendKeys(user.getFirstName());
        driver.findElement(middleName).sendKeys(user.getPatName());
        driver.findElement(radioButtonMale).click();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(user.getBirthday());
        formatter.applyPattern("dd.MM.yyyy");
        for (int i = 0; i < 8; i++) {
            driver.findElement(birthDate).sendKeys(Keys.BACK_SPACE);
        }
        //((JavascriptExecutor)driver).executeScript("arguments[0].value=arguments[1]", driver.findElement(birthDate), 01-01-2012);
        driver.findElement(birthDate).sendKeys(formatter.format(date));
        driver.findElement(radioButtonMale).click();
        driver.findElement(number).sendKeys(user.getNumber());
        driver.findElement(series).sendKeys(user.getSeries());
        driver.findElement(docTypeList).click();
        driver.findElement(passportRus).click();

        driver.findElement(issuedBy).sendKeys(oufms);
        for (int i = 0; i < 8; i++) {
            driver.findElement(issueDate).sendKeys(Keys.BACK_SPACE);
        }
        driver.findElement(issueDate).sendKeys(formatter.format(date));
        driver.findElement(snils).sendKeys(user.getSnils());
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(buttonGetCert));
        driver.findElement(buttonGetCert).click();
        return new MfcInformationPage(driver);
    }
}
