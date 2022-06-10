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
    private final By xmlRequest = By.xpath("//textarea[@id='xmlRequest']"); // поле с запросом
    private final By buttonSubmit = By.xpath("//button[@type='submit']"); // кнопка отправить
    private final By files = By.xpath("//textarea[@id='files']");
    private final By messageId = By.xpath("//div[@class='modal-body']//p/b");
    private final By buttonCloseMessageId = By.xpath("//button[@class='close']"); // кнопка закрыть на messageId

    public SmevPage(WebDriver driver) {this.driver = driver;}

    @Step("Получение из json файла пользователя для тестирования вакцины")
    private User getVaccineUser(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            user = mapper.readValue(new File("src/test/resources/vaccineUser.json"), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Step("Получение из json файла пользователя для тестирования вакцины")
    private User getIllnessUser() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            user = mapper.readValue(new File("src/test/resources/illnessUser.json"), User.class);
        return user;
    }

    @Step("Получение из json файла пользователя для тестирования медотвода")
    private User getAdmissionUser() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        user = mapper.readValue(new File("src/test/resources/admissionUser.json"), User.class);
        return user;
    }

    @Step("Внесение данных в поля СМЭВ для вакцины")
    private void subForVaccine(User user, Date date, int vacPhaseNum, int vcPhasesTot, long unrz){
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForVaccine(user, date, 1,1, rnd()));
        driver.findElement(buttonSubmit).click();
        driver.findElement(buttonCloseMessageId).click();
    }

    @Step("Внесение данных в поля СМЭВ для медотвода")
    private void subForAdmission(User user, Date date, long unrz, int type){
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForAdmission(user, date, unrz, type));
        driver.findElement(buttonSubmit).click();
        driver.findElement(buttonCloseMessageId).click();
    }

    @Step("Отправка активной однофазной вакцины, сделанной месяц назад")
    public Date submitVaccineSinglePhaseActive(WebDriver driver){
        this.driver = driver;
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        subForVaccine(user, date, 1, 1, rnd());
        return date;
    }

    @Step("Отправка однофазной вакцины от сегодняшнего дня")
    public Date submitVaccineSinglePhaseHasNotArrived(WebDriver driver){
        this.driver = driver;
        user = getVaccineUser();
        Date date = new Date();
        subForVaccine(user, date, 1, 1, rnd());
        return date;
    }

    @Step("Создание просроченной однофазной вакцины")
    public Date submitVaccineSinglePhaseOverdue(WebDriver driver){
        this.driver = driver;
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.YEAR, -1);
        Date date = cal.getTime();
        subForVaccine(user, date, 1, 1, rnd());
        return date;
    }

    @Step("Отправка первой фазы двухвазной вакцины")
    public Date submitVaccineFirstPhase(WebDriver driver){
        this.driver = driver;
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        long unrz = rnd();
        subForVaccine(user, date, 1, 2, unrz);
        return date;
    }

    @Step("Отправка активной двухфазной вакцины")
    public Date submitVaccineTwoPhaseActive(WebDriver driver){
        this.driver = driver;
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        long unrz = rnd();
        subForVaccine(user, date, 1, 2, unrz);
        date = new Date();
        subForVaccine(user, date, 2, 2, unrz);
        return date;
    }

    @Step("Отправка просроченной двухфазной вакцины")
    public Date submitVaccineTwoPhaseOverdue(WebDriver driver){
        this.driver = driver;
        user = getVaccineUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.add(Calendar.MONTH, -2);
        Date date = cal.getTime();
        long unrz = rnd();
        subForVaccine(user, date, 1, 2, unrz);
        cal.add(Calendar.MONTH, +1);
        date = cal.getTime();
        subForVaccine(user, date, 2, 2, unrz);
        return date;
    }

    @Step("Отправка активной переболезни")
    public Date submitIllActive(WebDriver driver) throws IOException {
        this.driver = driver;
        user = getIllnessUser();
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        unrz = rnd();
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForIll(user, date, unrz));
        driver.findElement(buttonSubmit).click();
        driver.findElement(buttonCloseMessageId).click();
        return date;
    }

    @Step("Отправка просроченной переболезни")
    public Date submitIllOverdue(WebDriver driver) throws IOException {
        this.driver = driver;
        user = getIllnessUser();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        unrz = rnd();
        driver.findElement(xmlRequest).clear();
        driver.findElement(xmlRequest).sendKeys(getSmevTextForIll(user, date, unrz));
        driver.findElement(buttonSubmit).click();
        driver.findElement(buttonCloseMessageId).click();
        return date;
    }

    @Step("Отправка актуального медотвода")
    public Date submitAdmissionActive(WebDriver driver) throws IOException {
        this.driver = driver;
        user = getAdmissionUser();
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        unrz = rnd();
        subForAdmission(user, date, unrz, 2);
        return date;
    }

    public Date submitAdmissionInfinite(WebDriver driver) throws IOException {
        this.driver = driver;
        user = getAdmissionUser();
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        unrz = rnd();
        subForAdmission(user, date, unrz, 3);
        return date;
    }

    @Step("Получение текста сообщения для вакцины")
    private String getSmevTextForVaccine(User user, Date date, int vacPhaseNum, int vcPhasesTot, long unrz){
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

    @Step("Получение текста сообщения о переболезни")
    private String getSmevTextForIll(User user, Date date, long urnz){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String smevText = "<ns1:InputData xmlns:ns1=\"https://www.gosuslugi.ru/vaccinecovid19/RegisterCovid/1.0.0\" env=\"UAT\"> " +
                "<ns1:UNRZ>" + unrz + "</ns1:UNRZ> \n" +
                "<ns1:FamilyName>" + user.getSurName() +"</ns1:FamilyName> \n" +
                "<ns1:FirstName>" + user.getFirstName() + "</ns1:FirstName> \n" +
                "<ns1:Patronymic>" + user.getPatName() + "</ns1:Patronymic> \n" +
                "<ns1:BirthDate>" + user.getBirthday() + "</ns1:BirthDate> \n" +
                "<ns1:SNILS>" + user.getSnils() + "</ns1:SNILS> \n" +
                "<ns1:IdentityDocument> <ns1:DocType>1</ns1:DocType>\n" +
                "<ns1:DocSer>4123</ns1:DocSer> \n" +
                "<ns1:DocNum>984579</ns1:DocNum> </ns1:IdentityDocument> \n" +
                "<ns1:PolicyDocumentType> <ns1:PolicyType>3</ns1:PolicyType> <ns1:PolicyNumber>1217480559645787</ns1:PolicyNumber> </ns1:PolicyDocumentType> <ns1:MkbCode>U07.1</ns1:MkbCode> \n" +
                "<ns1:MkbName>COVID-19, вирус идентифицирован</ns1:MkbName> \n" +
                "<ns1:DiseaseOutcomeDate>" + formatter.format(date) + "</ns1:DiseaseOutcomeDate> \n" +
                "<ns1:RFSubjectCode>16</ns1:RFSubjectCode> \n" +
                "<ns1:RFSubjectName>Республика Татарстан</ns1:RFSubjectName> <ns1:MOId>1.2.643.5.1.13.13.12.2.16.1208</ns1:MOId> \n" +
                "<ns1:MOName>ГАУЗ \"Городская поликлиника №3\"</ns1:MOName> </ns1:InputData>";
        return smevText;
    }

    @Step("Получение текста об медотводе")
    private String getSmevTextForAdmission(User user, Date date, long unrz, int type){
        String endDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (type == 2)
            endDate = formatter.format(addYear(date));
        String smevText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<tns:RegisterDeliverytoEPGU env=\"EPGU\" xmlns:tns=\"https://www.gosuslugi.ru/vaccinecovid19/RegisterMedotvod/1.0.0\">\n" +
                "    <tns:UNRZ>" + unrz + "</tns:UNRZ>\n" +
                "    <tns:LastName>" + user.getSurName() + "</tns:LastName>\n" +
                "    <tns:FirstName>" + user.getFirstName() + "</tns:FirstName>\n" +
                "    <tns:Patronymic>" + user.getPatName() + "</tns:Patronymic>\n" +
                "    <tns:BirthDate>" + user.getBirthday() + "</tns:BirthDate>\n" +
                "    <tns:SNILS>" + user.getSnils() + "</tns:SNILS>\n" +
                "    <tns:PersDUL>\n" +
                "        <tns:Code>1</tns:Code>\n" +
                "        <tns:Series>0001</tns:Series>\n" +
                "        <tns:Number>600600</tns:Number>\n" +
                "    </tns:PersDUL>\n" +
                "    <tns:PersOMS>\n" +
                "        <tns:Type>" + type +"</tns:Type>\n" +
                "        <tns:Number>9247174389744329</tns:Number>\n" +
                "    </tns:PersOMS>\n" +
                "    <tns:Admission>2</tns:Admission>\n" +
                "    <tns:AdmissionStartDate>" + formatter.format(date) + "</tns:AdmissionStartDate>\n" +
                "    <tns:AdmissionEndDate>" + endDate + "</tns:AdmissionEndDate>\n" +
                "    <tns:MO>\n" +
                "        <tns:HostClinicId>1.2.643.5.1.13.13.12.2.50.17944</tns:HostClinicId>\n" +
                "        <tns:HostClinicName>ГБУЗ МО \"СОЛНЕЧНОГОРСКАЯ ОБЛАСТНАЯ БОЛЬНИЦА\"</tns:HostClinicName>\n" +
                "        <tns:ClinicRegion>00</tns:ClinicRegion>\n" +
                "        <tns:ClinicId>1.2.643.5.1.13.13.12.2.50.17944.0.404035</tns:ClinicId>\n" +
                "        <tns:ClinicName>Тимоновская поликлиника</tns:ClinicName>\n" +
                "        <tns:ClinicAddress>Солнечногорск-7, ул.Подмосковная, д.7</tns:ClinicAddress>\n" +
                "        <tns:ClinicPhone>4959943647</tns:ClinicPhone>\n" +
                "    </tns:MO>\n" +
                "</tns:RegisterDeliverytoEPGU>\n";
        return smevText;
    }

    private static long rnd()
    {
        Random random = new Random();
        long i = random.nextLong();
        if (i < 0) i = i * (-1);
        return i/100;
    }

    private static Date addYear(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }
}
