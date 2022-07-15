package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private WebDriver driver;
    private final By enterButton = By.xpath("//a[@class='link ng-star-inserted']"); // войти

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Нажатие на кнопку войти")
    public LoginPage enter(WebDriver driver){
        this.driver = driver;
        driver.findElement(enterButton).click();
        return new LoginPage(driver);
    }

    @Step("Открыть основную страницу")
    public MainPage getMainPage(String url){
        driver.get(url);
        return new MainPage(driver);
    }

    @Step("Открыть страницу с ковидом 10602/1")
    public CovidPage getCovidPage(WebDriver driver){
        this.driver = driver;
        String url = driver.getCurrentUrl();
        driver.get(url + "10602/1");
        return new CovidPage(driver);
    }

    @Step("Открыть страницу с витринами 10700/1")
    public EqueuePage getEqueuePage(WebDriver driver){
        this.driver = driver;
        String url = driver.getCurrentUrl();
        driver.get(url + "10700/1/form");
        return new EqueuePage(driver);
    }

    @Step("Открыть страницу с АМР МФЦ")
    public MfcPage getMfcPage(WebDriver driver){
        this.driver = driver;
        String url = driver.getCurrentUrl();
        driver.get(url + "covid-status");
        return new MfcPage(driver);
    }

    @Step("Открыть страницу с ЭЛН")
    public ElnPage getElnPage(WebDriver driver){
        this.driver = driver;
        driver.get("https://eln-uat.test.gosuslugi.ru/");
        return new ElnPage(driver);
    }

    @Step("Получить токен пользователя МФЦ")
    public String getTokenMfc(WebDriver driver){
        this.driver = driver;
        String token = driver.manage().getCookieNamed("acc_t").toString();
        return token;
    }
}
