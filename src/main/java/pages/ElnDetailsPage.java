package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElnDetailsPage {
    private WebDriver driver;
    private final By primaryTag = By.xpath("//div[@class='primary-tag ng-star-inserted']");
    private final String open = "Открыт";

    public ElnDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка статуса Открытый для ЭЛН")
    public void checkOpenStatus(WebDriver driver){
        String tag = driver.findElement(primaryTag).getText();
        Assert.assertEquals(tag, open);
    }
}
