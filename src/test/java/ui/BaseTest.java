package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected String url_dev = "https://pgu-dev-fed.test.gosuslugi.ru/";
    protected String url_dev2 = "https://pgu-dev2-fed.test.gosuslugi.ru/";
    protected String url_uat = "https://pgu-uat-fed.test.gosuslugi.ru/";
    protected String esia1 = "https://esia-portal1.test.gosuslugi.ru/login/";
    protected String LOGIN_KUBRIK = "+79555555001";
    protected String PASS_KUBRIK = "!Qq797979";
    protected String oid_Kubrik = "1000483405";

    public WebDriver getDriver() { return this.driver;}

    public WebDriver start(String url){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehavior", "dismiss");
        caps.setCapability("acceptInsecureCerts", "true");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        return driver;
    }

    public void setDown(WebDriver driver){
        if (driver != null){
            driver.quit();
        }
    }
}
