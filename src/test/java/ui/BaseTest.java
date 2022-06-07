package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected String URL_DEV = "https://pgu-dev-fed.test.gosuslugi.ru/";
    protected String URL_DEV2 = "https://l52.epgu-front.test.gosuslugi.ru/";
    protected String URL_UAT= "https://pgu-uat-fed.test.gosuslugi.ru/";
    protected String ESIA = "https://esia-portal1.test.gosuslugi.ru/login/";
    protected String SMEVUAT = "https://pgu-uat-fednlb.test.gosuslugi.ru/smevadapter/requestToPgu";
    protected String SMEVDEV2 = "https://pgu-dev2-fednlb.test.gosuslugi.ru/smevadapter/requestToPgu";
    protected String LOGIN_KUBRIK = "+79555555001";
    protected String PASS_KUBRIK = "!Qq797979";
    protected String OID_KUBRIK = "1000483405";
    protected String LOGIN_TARANTION = "+79555555009";
    protected String PASS_TARANTINO = "!Qq797979";
    protected String OID_TARANTINO = "3078943604";

    public WebDriver getDriver() { return this.driver;}

    @Step("Запуск браузера")
    public WebDriver start(String url){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehavior", "dismiss");
        caps.setCapability("acceptInsecureCerts", "true");
        caps.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "complete");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        return driver;
    }

    @Step("Закрытие браузера")
    public void setDown(WebDriver driver){
        if (driver != null){
            driver.quit();
        }
    }
}
