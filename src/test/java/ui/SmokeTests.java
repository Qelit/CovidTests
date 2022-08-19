package ui;

import api.TestAssured;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pages.*;
import sql.ConnectionStands;
import sql.Sql;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class SmokeTests extends BaseTest {
    WebDriver driver;

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Отсутствует вакцина")
    public void vaccineEmpty() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
                .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = new DriverManager().start(SMEVUAT);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO)
                .enterPassword(PASS_TARANTINO).enterClick().getMainPage(URL_UAT).getCovidPage(driver).getEmptyQr(driver);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Вакцинация первым компонентом двухфазной вакцины")
    public void vaccineFirstPhase() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineFirstPhase(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO).enterClick()
                .getMainPage(URL_UAT).getCovidPage(driver).getEmptyQr(driver);
        new Sql().checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1).checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 0)
        .checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 0);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Проверка активной однофазной вакцины")
    public void vaccineSingleActive() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
        new Sql().checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1).checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Проверка однофазной вакцины, срок которой не наступил")
    public void vaccineSingleHasNotArrived() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseHasNotArrived(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getNotArrivedStatus(driver);
        new Sql().checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1).checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Проверка просроченной однофазной вакцины")
    public void vaccineSingleOverdue() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseOverdue(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getOverdueStatus(driver);
        new Sql().checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1).checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description("Проверка актуальной двухфазной вакцины")
    public void vaccineTwoPhasesActive() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineTwoPhaseActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
        new Sql().checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 2).checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description("Проверка истекшей двухфазной вакцины")
    public void vaccineTwoPhasesOverdue() throws SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineTwoPhaseOverdue(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getOverdueStatus(driver);
        new Sql().checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 2).checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description("Проверка ревакцинации при добавлении первой фазы вакцины")
    public void revaccineFirstPhase() throws SQLException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        Date vacDate = smevPage.submitVaccineTwoPhaseOverdue(driver);
        smevPage.submitVaccineFirstPhase(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver);
        String date = new CovidPage(driver).checkDateOverdueCert(driver);
        StatusPage statusPage = new CovidPage(driver).getQRUrl(driver);
        statusPage.getOverdueStatus(driver, date, vacDate);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Ревакцинация двухфазной вакциной")
    public void revaccineTwoPhases() throws ParseException, SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineTwoPhaseOverdue(driver);
        Date vacDate = new SmevPage(driver).submitVaccineTwoPhaseActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).checkDateForVaccine(driver, vacDate);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Ревацинация однокомпонентой вакциной")
    public void revaccineSinglePhase() throws ParseException, SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineTwoPhaseOverdue(driver);
        Date vacDate = new SmevPage(driver).submitVaccineSinglePhaseActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).checkDateForVaccine(driver, vacDate);
    }

    @Test
    @Feature(value = "Переболезнь")
    @Description(value = "Проверка активного сертификата по переболезни")
    public void illnessActive() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitIllActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
        new Sql().checkInCovidRegisterRecord(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Переболезнь")
    @Description(value = "Проверка просроченного сертификата по переболезни")
    public void illnessOverdue() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitIllOverdue(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getOverdueStatus(driver);
        new Sql().checkInCovidRegisterRecord(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Переболезнь")
    @Description("Добавление переболезни к просроченной переболезни")
    public void reIllness() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitIllOverdue(driver);
        Date illDate = new SmevPage(driver).submitIllActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).checkDateForVaccine(driver, illDate);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description("Создание срочного медотвода для пользователя с отсутствующим сертификатом")
    public void admissionForNoCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusUntil(driver).getEmptyStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователей с отсутствующим сертификатом")
    public void admissionInfinityForNoCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusInfinity(driver).getEmptyStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователя с активным сертификатом по вакцине")
    public void admissionInfinityForActiveCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseActive(driver);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusInfinity(driver).getActiveStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователя с просроченным сертификатом по вакцине")
    public void admissionInfinityForOverdueCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseOverdue(driver);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusInfinity(driver).getOverdueStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователя с не наступившим сертификатом по вакцине")
    public void admissionInfinityForNotArrivedCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseHasNotArrived(driver);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusInfinity(driver).getNotArrivedStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание срочного медотвода для пользователя с активным сертификатом по вакцине")
    public void admissionForActiveCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseActive(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusUntil(driver).getActiveStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание срочного медотвода для пользователя с просроченным сертификатом по вакцине")
    public void admissionForOverdueCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseOverdue(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusUntil(driver).getOverdueStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание срочного медотвода для пользователя с не наступившим сертификатом по вакцине")
    public void admissionForNotArrivedCert() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseHasNotArrived(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getAdmissionStatusUntil(driver).getNotArrivedStatus(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1).checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1)
        .checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1).checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание более позднего медотвода на уже существующий ранний медотвод")
    public void admissionForLater() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        Date date = new SmevPage(driver).submitAdmissionEarly(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).checkDateForAdmission(driver, date);
        new CovidPage(driver).getQRUrl(driver).getAdmissionStatusUntil(driver);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 2).checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 2)
        .checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 2);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание более раннего медотвода на уже существующий поздний медотвод")
    public void admissionForEarly() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        Date firstDate = new SmevPage(driver).submitAdmissionActive(driver);
        Date date = new SmevPage(driver).submitAdmissionEarly(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).checkDateForAdmission(driver, date);
        new CovidPage(driver).getQRUrl(driver).getAdmissionStatusUntil(driver, firstDate);
        new Sql().checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 2).checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 2)
        .checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 2);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Получение сертификата МФЦ для пользователя с активной вакциной")
    public void mfcVaccineActive() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveVaccineCert(driver).getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание бессрочного медотвода для пользователя, у которого нет общего сертификата МФЦ")
    public void mfcAdmissionInfinity() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver).getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание срочного медотвода для пользователя, у которого нет сертификат активен. МФЦ")
    public void mfcAdmissionInfiniteForActiveCert() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineTwoPhaseActive(driver);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver).getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание бессрочного медотвода для пользователя, у которого сертификат пророчен. МФЦ")
    public void mfcAdmissionInfiniteForOverdueCert() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseOverdue(driver);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC).enterClick().getMainPage(URL_UAT)
        .getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver).getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание бессрочного медотвода для пользователя, у которого сертификат не наступил. МФЦ")
    public void mfcAdmissionInfiniteForOverdueCertNotArrived() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseHasNotArrived(driver);
        new SmevPage(driver).submitAdmissionInfinite(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver)
        .getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание срочного медотвода для пользователя, у которого сертификат активен. МФЦ")
    public void mfcAdmissionActiveForActiveCert() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineTwoPhaseActive(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver)
        .getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание срочного медотвода для пользователя, у которого сертификат просрочен. МФЦ")
    public void mfcAdmissionActiveForOverdueCert() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseOverdue(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver)
        .getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание срочного медотвода для пользователя, у которого сертификат не наступил. МФЦ")
    public void mfcAdmissionActiveForNotArrivedCert() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitVaccineSinglePhaseHasNotArrived(driver);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver)
        .getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Создание срочного медотвода для пользователя, у которого сертификат отсутствует. МФЦ")
    public void mfcAdmissionActive() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver)
        .getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Поиск по пользователю не имеющему сертификатов. МФЦ")
    public void mfcHaveNotCert() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
       .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(URL_UAT);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver).getActiveAdmissionCert(driver)
        .getFalseStatusPerson(driver);
    }

    @Test
    @Feature(value = "Детские сертификаты")
    @Description("Создание сертификата переболевшего")
    public void childrenCerts() throws SQLException, ParseException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitIllActiveForChild(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_MFC).enterPassword(PASS_MFC)
        .enterClick().getMainPage(URL_UAT).getMfcPage(driver).enterEmployer(driver);
        String token = new MfcPage(driver).getTokenMfc(driver);
        new TestAssured().getChildCert(URL_UAT_REST, token);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Получение элн по номеру")
    public void getElnDetailsForNumber(){
        driver = start(URL_UAT);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_ELN).enterPassword(PASS_ELN)
        .enterClick().getMainPage(URL_UAT).getElnPage(driver).getElnForNumber(ELN_NUMBER);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Получение элн по периоду")
    public void getElnDetailsForPeriod(){
        driver = start(URL_UAT);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_ELN).enterPassword(PASS_ELN)
        .enterClick().getMainPage(URL_UAT).getElnPage(driver).getElnFor6Monthes(driver).clickMore(driver).checkOpenStatus(driver);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Отсутствие элн за период")
    public void noElnForPeriod(){
        driver = start(URL_UAT);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_ELN).enterPassword(PASS_ELN)
        .enterClick().getMainPage(URL_UAT).getElnPage(driver).getElnFor1Month(driver).checkNotFound(driver);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Отсутствие элн за выбранный период")
    public void getElnForSelectPeriod(){
        driver = start(URL_UAT);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_ELN).enterPassword(PASS_ELN)
        .enterClick().getMainPage(URL_UAT).getElnPage(driver).getElnForNewPeriod(driver).checkNotFound(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка создания сертификата по положительному тесту на антитела LgG")
    public void CertForAntibodiesLgG() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver)
        .openCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности создания сертификата по положительному тесту на антитела LgG")
    public void NoCertForPositiveAntibodiesLgG() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGFalseAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности создания сертификата по отрицательному и после этого положительному тесту на антитела LgG")
    public void NoCertForPositiveAndNegativeAntibodiesLgG() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new SmevPage(driver).submitLgGFalseAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности генерации сертификата для положительного теста на антитела LgM")
    public void NoCertForPositiveAntibodiesLgM() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgMFalseAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности генерации сертификата для отрицательного теста на антитела LgM")
    public void NoCertForNegativeAntibodiesLgM() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgMTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности генерации сертификата для отрицательного теста на антитела LgG + LgM")
    public void NoCertForNegativeAntibodiesLgGLgM() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGLgMTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности генерации сертификата для отрицательного теста на антитела LgG + LgM")
    public void NoCertForPositiveAntibodiesLgGLgM() throws IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGLgMFalseAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности генерации сертификата для отрицательного просроченного теста на антитела LgG")
    public void NoCertForNegativeAntibodiesLgGOverdue() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodiesOverdue(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка отсутствия возможности генерации сертификата для положительного просроченного теста на антитела LgG")
    public void NoCertForPositiveAntibodiesLgGOverdue() throws IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGFalseAntibodiesOverdue(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).checkNotButtonFormCert(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("Проверка создания сертификата по положительному тесту на антитела LgG и наличию медотвода")
    public void CertForAntibodiesLgGAndAdmission() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitAdmissionActive(driver);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver).openCovidPage(driver)
        .getQRUrl(driver).getActiveStatus(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("С имеющимся сертификатом по антителам, новый положительный тест на антитела. Попытка формирования")
    public void CertForAntibodiesLgGAndTrueAntibodies() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        new SmevPage(driver).getSmevPage(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("С имеющимся сертификатом по антителам, новый отрицательный тест на антитела. Попытка формирования")
    public void CertForAntibodiesLgGAndFalseAntibodies() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        new SmevPage(driver).getSmevPage(SMEVUAT);
        new SmevPage(driver).submitLgGFalseAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Добавить медотвод.")
    public void CertForAntibodiesLgGAndAfterAdmission() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        new SmevPage(driver).getSmevPage(SMEVUAT);
        new SmevPage(driver).submitAdmissionActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Добавить вакцину.")
    public void CertForAntibodiesLgGAndAfterVaccine() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        Date antibodiesDate = new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver).openCovidPage(driver)
        .checkDateForCertAntibodies(driver, antibodiesDate);
        new SmevPage(driver).getSmevPage(SMEVUAT);
        Date vaccineDate = new SmevPage(driver).submitVaccineSinglePhaseActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).checkDateForVaccine(driver, vaccineDate);
        new CovidPage(driver).getQRUrl(driver).getActiveStatus(driver, vaccineDate);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Добавить сертификат переболевшего.")
    public void CertForAntibodiesLgGAndAfterIllness() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        Date antibodiesDate = new SmevPage(driver).submitLgGTrueAntibodies(driver);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver)
        .openCovidPage(driver).checkDateForCertAntibodies(driver, antibodiesDate);
        new SmevPage(driver).getSmevPage(SMEVUAT);
        Date vaccineDate = new SmevPage(driver).submitIllActive(driver);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).checkDateForVaccine(driver, vaccineDate);
        new CovidPage(driver).getQRUrl(driver).getActiveStatus(driver, vaccineDate);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Отправляем антитела с другим результатом")
    public void CertForAntibodiesAndAntibodiesWithAnotherResult() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        long unrz = smevPage.rnd();
        smevPage.submitLgGTrueAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        smevPage.getSmevPage(SMEVUAT);
        smevPage.submitLgGFalseAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getEmptyQr(driver);
        new Sql().checkInCovidStatusCertAntibodies(ConnectionStands.UAT, OID_TARANTINO);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Отправляем антитела с другим типом теста")
    public void CertForAntibodiesAndAntibodiesWithAnotherType() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        long unrz = smevPage.rnd();
        smevPage.submitLgGTrueAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        smevPage.getSmevPage(SMEVUAT);
        smevPage.submitLgMTrueAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getEmptyQr(driver);
        new Sql().checkInCovidStatusCertAntibodies(ConnectionStands.UAT, OID_TARANTINO);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Отправляем антитела с текущим unrz на другой oid")
    public void CertForAntibodiesAndAnotherOid() throws SQLException, IOException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        long unrz = smevPage.rnd();
        smevPage.submitLgGTrueAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        smevPage.getSmevPage(SMEVUAT);
        smevPage.submitLgGAntibodiesForAnotherOid(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getEmptyQr(driver);
        new Sql().checkInCovidStatusCertAntibodies(ConnectionStands.UAT, OID_TARANTINO);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Отправляем антитела с другой датой")
    public void CertForAntibodiesAndAnotherDate() throws SQLException, IOException, ParseException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        long unrz = smevPage.rnd();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, - 1);
        Date date = cal.getTime();
        smevPage.submitLgGTrueAntibodies(driver, unrz, date);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver).openCovidPage(driver)
        .checkDateForCertAntibodies(driver, date);
        smevPage.getSmevPage(SMEVUAT);
        date = smevPage.submitLgGTrueAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).checkDateForCertAntibodies(driver, date);
        new CovidPage(driver).getQRUrl(driver).getActiveStatusAntibodies(driver, date);
    }

    @Test
    @Feature("Антитела")
    @Description("У пользователя присутствует сертификат по антителам. Отправляем антитела с другими значениями, но не oid/результат/тип теста/дата")
    public void CertForAntibodiesAndAnotherText() throws IOException, SQLException {
        new Sql().deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO).deleteCovidStatusCertForOid(ConnectionStands.UAT, OID_TARANTINO)
        .deleteCovidTestFromMoForSnils(ConnectionStands.UAT, SNILS_TARANTINO).deleteCovidTestFromMoDpForOid(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO).prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO)
        .prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        long unrz = smevPage.rnd();
        smevPage.submitLgGTrueAntibodies(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_TARANTINO).enterPassword(PASS_TARANTINO)
        .enterClick().getMainPage(URL_UAT).getCovidPage(driver).getAntibodiesPage(driver).formCert(driver);
        smevPage.getSmevPage(SMEVUAT);
        smevPage.submitLgGAntibodiesForAnotherText(driver, unrz);
        new MainPage(driver).getMainPage(URL_UAT).getCovidPage(driver).getQRUrl(driver).getActiveStatus(driver);
    }

    @Test
    @Feature("Витрины")
    @Description("Проверка открытия страницы с витринами и выбор себя")
    public void getEqueue(){
        driver = start(URL_UAT);
            new MainPage(driver).getMainPage(URL_UAT).enter(driver).enterUserName(LOGIN_EQUEUE)
                    .enterPassword(PASS_EQUEUE).enterClick().getMainPage(URL_UAT).getEqueuePage(driver).startAppointment()
                    .pickMySelf().checkOpen();
    }

    @Test
    public void getToken(){
        TestAssured testAssured = new TestAssured();
        String token = testAssured.getToken(URL_UAT_FEDLKAPINLB, OID_TARANTINO);
    }

    @After()
    public void tearDown() {
        makeScreenshot();
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
