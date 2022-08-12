package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.ElnDetailsPage;

public class ElnResultPage {
    protected WebDriver driver;
    protected final By more = By.xpath("//lib-button[@type='anchor']");
    protected final By checkNotFound = By.xpath("//div[@class='image text-center']/following-sibling::div");
    protected final String notFound = "За выбранный период времени данные не найдены.";

    public ElnResultPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать кнопку подробнее")
    public ElnDetailsPage clickMore(WebDriver driver){
        driver.findElement(more).click();
        return new ElnDetailsPage(driver);
    }

    @Step("Проверка отсутствия ЭЛН за период")
    public void checkNotFound(WebDriver driver){
        String check = driver.findElement(checkNotFound).getText();
        Assert.assertEquals(check, notFound);
    }
}
