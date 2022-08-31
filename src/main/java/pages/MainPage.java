package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private WebDriver driver;
    private final By enterButton = By.xpath("//a[@class='link ng-star-inserted']"); // войти
    private final By burger = By.xpath("//div[@class='catalog-menu-wrap ng-star-inserted']/lib-menu-catalog"); //бургер
    private final By health = By.xpath("//a[@class='catalog-link-item ng-star-inserted']"); //здоровье
    private final By orderToOldDoctor = By.xpath("//div[@class='ps-content']//h4[text()=' Запись на прием к врачу ']");
    private final By orderToCallingDoctor = By.xpath("//div[@class='hide-lg mob-container-inner']//h4[text()=' Вызов врача на дом ']"); //вызов врача на дом

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Нажатие на кнопку войти")
    public LoginPage enter(WebDriver driver){
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
        String url = driver.getCurrentUrl();
        driver.get(url + "10602/1");
        return new CovidPage(driver);
    }

    @Step("Открыть страницу с витринами 10700/1")
    public EqueuePage getEqueuePage(WebDriver driver){
        String url = driver.getCurrentUrl();
        driver.get(url + "10700/1/form");
        return new EqueuePage(driver);
    }

    @Step("Открыть страницу с АМР МФЦ")
    public MfcPage getMfcPage(WebDriver driver){
        String url = driver.getCurrentUrl();
        driver.get(url + "covid-status");
        return new MfcPage(driver);
    }

    @Step("Открыть страницу с ЭЛН")
    public ElnPage getElnPage(WebDriver driver){
        driver.get("https://eln-uat.test.gosuslugi.ru/");
        return new ElnPage(driver);
    }

    @Step("Получить токен пользователя МФЦ")
    public String getTokenMfc(WebDriver driver){
        String token = driver.manage().getCookieNamed("acc_t").toString();
        return token;
    }

    public void clickBurger(WebDriver driver){
        driver.findElement(burger).click();
    }

    public OldDoctorPage openOldDoctorPage(WebDriver driver){
        clickBurger(driver);
        driver.findElement(health).click();
        driver.findElement(orderToOldDoctor).click();
        return new OldDoctorPage(driver);
    }

    public CallingDoctorAtHome openCallingDoctorAtHome(WebDriver driver){
        clickBurger(driver);
        WebElement element = driver.findElement(health);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        WebElement element1 = driver.findElement(orderToCallingDoctor);
        executor.executeScript("arguments[0].click();", element1);;
        return new CallingDoctorAtHome(driver);
    }
}
