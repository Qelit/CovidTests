package ui.pages;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import users.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SmevPage {

    private WebDriver driver;
    private User user;
    private long unrz;
    private final By xmlRequest = By.xpath("//textarea[@id='xmlRequest']");
    private final By buttonSubmit = By.xpath("//button[@type='submit']");
    private final By files = By.xpath("//textarea[@id='files']");
    private final By messageId = By.xpath("//div[@class='modal-body']//p/b");
    private final By buttonCloseMessageId = By.xpath("//button[@class='close']");

    public SmevPage(WebDriver driver) {this.driver = driver;}

    @Step("Получение из json файла пользователя для тестирования вакцины")
    public User getVaccineUser(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            user = mapper.readValue(new File("src/test/resources/vaccineUser.json"), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Step("Отправка активной однофазной вакцины, сделанной месяц назад")
    public void submitVaccineSinglePhaseActive(WebDriver driver){
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,1, rnd()));
        driver.findElement(buttonSubmit).click();
    }

    @Step("Отправка однофазной вакцины от сегодняшнего дня")
    public void submitVaccineSinglePhaseHasNotArrived(WebDriver driver){
        user = getVaccineUser();
        Date date = new Date();
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,1, rnd()));
        driver.findElement(buttonSubmit).click();
    }

    @Step("Создание просроченной однофазной вакцины")
    public void submitVaccineSinglePhaseOverdue(WebDriver driver){
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.YEAR, -1);
        Date date = cal.getTime();
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,1, rnd()));
        driver.findElement(buttonSubmit).click();
    }

    @Step("Отправка первой фазы двухвазной вакцины")
    public void submitVaccineFirstPhase(WebDriver driver){
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        long unrz = rnd();
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,2, unrz));
        driver.findElement(buttonSubmit).click();
    }

    @Step("Отправка активной двухфазной вакцины")
    public void submitVaccineTwoPhaseActive(WebDriver driver){
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        driver.findElement(xmlRequest).clear();
        long unrz = rnd();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,2, unrz));
        driver.findElement(buttonSubmit).click();
        driver.findElement(buttonCloseMessageId).click();
        driver.findElement(xmlRequest).clear();
        date = new Date();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 2,2, unrz));
        driver.findElement(buttonSubmit).click();
    }

    @Step("Отправка просроченной двухфазной вакцины")
    public void submitVaccineTwoPhaseOverdue(WebDriver driver){
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.add(Calendar.MONTH, -2);
        Date date = cal.getTime();
        //driver.findElement(files).clear();
        driver.findElement(xmlRequest).clear();
        long unrz = rnd();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,2, unrz));
        driver.findElement(buttonSubmit).click();
        driver.findElement(buttonCloseMessageId).click();
        driver.findElement(xmlRequest).clear();
        cal.add(Calendar.MONTH, +1);
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 2,2, unrz));
        driver.findElement(buttonSubmit).click();
    }

    @Step("Получение текста сообщения для вакцины")
    public String getSmevTextForVaccine(User user, Date date, int vacPhaseNum, int vcPhasesTot, long unrz){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String smevText = "<RegisterDeliverytoEPGU xmlns=\"https://www.gosuslugi.ru/vaccinecovid19/RegisterToEpgu/1.3.0\" env=\"EPGU\">\n" +
                "  <EPGULoadFromRegister>\n" +
                "<EPGUvaccDataFromRegister>    \n" +
                "            <vacFirstName>"+ user.getFirstName() +"</vacFirstName>    \n" +
                "            <vacLastName>" + user.getSurName() +"</vacLastName>    \n" +
                "            <vacPatName>" + user.getPatName() + "</vacPatName>    \n" +
                "            <vacBDate>" + user.getBirthday() + "</vacBDate>    \n" +
                "            <vacSNILS>" + user.getSnils() + "</vacSNILS>    \n" +
                "            <vacPersDUL>    \n" +
                "                <Code>1</Code>    \n" +
                "                <Series>4123</Series>    \n" +
                "                <Number>984579</Number>    \n" +
                "                                \n" +
                "            </vacPersDUL>    \n" +
                "            <vacPersOMS>    \n" +
                "                <Type>3</Type>    \n" +
                "                <Number>1217480559645782</Number>    \n" +
                "                                \n" +
                "            </vacPersOMS>    \n" +
                "            <vacDate>"+ formatter.format(date) +"</vacDate>    \n" +
                "            <vacPhaseNum>" + vacPhaseNum + "</vacPhaseNum>    \n" +
                "            <vacMO>    \n" +
                "                <HostClinicId>1.2.643.5.1.13.13.12.2.77.7808</HostClinicId>    \n" +
                "                <HostClinicName>ГБУЗ \"КДП № 121 ДЗМ\"</HostClinicName>    \n" +
                "                <ClinicRegion>77</ClinicRegion>    \n" +
                "                <ClinicId/>    \n" +
                "                <ClinicName/>    \n" +
                "                <ClinicAddress/>    \n" +
                "                                \n" +
                "            </vacMO>    \n" +
                "            <vaccineData>    \n" +
                "                <vcName>Спутник Лайт Векторная вакцина для профилактики коронавирусной инфекции, вызываемой вирусом SARS-CoV-2</vcName>    \n" +
                "                <vcState>Россия</vcState>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>1</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>2</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>3</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>7</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>14</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>15</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>16</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>21</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>28</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcSurvDiary>    \n" +
                "                    <Quant>42</Quant>    \n" +
                "                    <QuantDim>DD</QuantDim>    \n" +
                "                                        \n" +
                "                </vcSurvDiary>    \n" +
                "                <vcPhasesTot>"+ vcPhasesTot +"</vcPhasesTot>\n" +
                "<vcSurvVac> \n" +
                "          <Quant>3</Quant> \n" +
                "          <QuantDim>MM</QuantDim> \n" +
                "        </vcSurvVac>     \n" +
                "                <batch_series>100621</batch_series>                      \n" +
                "            </vaccineData>    \n" +
                "            <UNRZ>"+ unrz +"</UNRZ>\n" +
                "</EPGUvaccDataFromRegister>\n" +
                "  </EPGULoadFromRegister>\n" +
                "</RegisterDeliverytoEPGU>";
        return smevText;
    }

    public static long rnd()
    {
        Random random = new Random();
        long i = random.nextLong();
        if (i < 0) i = i * (-1);
        return i/100;
    }
}
