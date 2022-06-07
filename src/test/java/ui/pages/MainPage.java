package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private WebDriver driver;
    private final By enterButton = By.xpath("//a[@class='link ng-star-inserted']");

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
        this.driver = driver;
        String url = driver.getCurrentUrl();
        driver.get(url + "10602/1");
        return new CovidPage(driver);
    }
}
