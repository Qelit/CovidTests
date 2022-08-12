package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private final By loginInput = By.xpath("//input[@id='login']"); // поле логина
    private final By passInput = By.xpath("//input[@id='password']"); // поле пароля
    private final By logInButton = By.xpath("//button[text()='Войти']"); // войти

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Ввод логина")
    public LoginPage enterUserName(String login) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(loginInput));
        WebElement element = driver.findElement(loginInput);
        element.sendKeys(login);
        return this;
    }

    @Step("Ввод пароля")
    public LoginPage enterPassword(String password) {
        driver.findElement(passInput).sendKeys(password);
        return this;
    }

    @Step("Нажать войти")
    public MainPage enterClick(){
        driver.findElement(logInButton).click();
        return new MainPage(driver);
    }

}
