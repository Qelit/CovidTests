package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private WebDriver driver;
    private final By enterButton = By.xpath("//a[@class='link ng-star-inserted']");

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public LoginPage enter(WebDriver driver){
        driver.findElement(enterButton).click();
        return new LoginPage(driver);
    }

    public MainPage getMainPage(String url){
        driver.get(url);
        return new MainPage(driver);
    }

    public CovidPage getCovidPage(WebDriver driver){
        this.driver = driver;
        String url = driver.getCurrentUrl();
        driver.get(url + "10602/1");
        return new CovidPage(driver);
    }
}
