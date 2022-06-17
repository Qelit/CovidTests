package ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import ui.sql.ConnectionStands;
import ui.sql.Sql;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public class SmokeTests extends BaseTest{
    WebDriver driver;

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Отсутствует вакцина")
    public void vaccineEmpty() throws SQLException {
        Sql sql = new Sql();
        sql.deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.getEmptyQr(driver);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Вакцинация первым компонентом двухфазной вакцины")
    public void vaccineFirstPhase() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineFirstPhase(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.getEmptyQr(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 0);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 0);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Проверка активной однофазной вакцины")
    public void vaccineSingleActive() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getActiveStatus(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Проверка однофазной вакцины, срок которой не наступил")
    public void vaccineSingleHasNotArrived() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseHasNotArrived(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getNotArrivedStatus(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Проверка просроченной однофазной вакцины")
    public void vaccineSingleOverdue() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseOverdue(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getOverdueStatus(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description("Проверка актуальной двухфазной вакцины")
    public void vaccineTwoPhasesActive() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineTwoPhaseActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getActiveStatus(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 2);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description("Проверка истекшей двухфазной вакцины")
    public void vaccineTwoPhasesOverdue() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineTwoPhaseOverdue(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getOverdueStatus(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 2);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description("Проверка ревакцинации при добавлении первой фазы вакцины")
    public void revaccineFirstPhase() throws SQLException, ParseException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        Date vacDate = smevPage.submitVaccineTwoPhaseOverdue(driver);
        smevPage.submitVaccineFirstPhase(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        String date = covidPage.checkDateOverdueCert(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getOverdueStatus(driver, date, vacDate);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Ревакцинация двухфазной вакциной")
    public void revaccineTwoPhases() throws ParseException, SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineTwoPhaseOverdue(driver);
        Date vacDate = smevPage.submitVaccineTwoPhaseActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.checkDateForVaccine(driver, vacDate);
    }

    @Test
    @Feature(value = "Вакцина")
    @Description(value = "Ревацинация однокомпонентой вакциной")
    public void revaccineSinglePhase() throws ParseException, SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineTwoPhaseOverdue(driver);
        Date vacDate = smevPage.submitVaccineSinglePhaseActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.checkDateForVaccine(driver, vacDate);
    }

    @Test
    @Feature(value = "Переболезнь")
    @Description(value = "Проверка активного сертификата по переболезни")
    public void illnessActive() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitIllActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getActiveStatus(driver);
        sql.checkInCovidRegisterRecord(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Переболезнь")
    @Description(value = "Проверка просроченного сертификата по переболезни")
    public void illnessOverdue() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitIllOverdue(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getOverdueStatus(driver);
        sql.checkInCovidRegisterRecord(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Переболезнь")
    @Description("Добавление переболезни к просроченной переболезни")
    public void reIllness() throws SQLException, IOException, ParseException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitIllOverdue(driver);
        Date illDate = smevPage.submitIllActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.checkDateForVaccine(driver, illDate);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description("Создание срочного медотвода для пользователя с отсутствующим сертификатом")
    public void admissionForNoCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitAdmissionActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusUntil(driver);
        statusPage.getEmptyStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователей с отсутствующим сертификатом")
    public void admissionInfinityForNoCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitAdmissionInfinite(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusInfinity(driver);
        statusPage.getEmptyStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователя с активным сертификатом по вакцине")
    public void admissionInfinityForActiveCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseActive(driver);
        smevPage.submitAdmissionInfinite(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusInfinity(driver);
        statusPage.getActiveStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователя с просроченным сертификатом по вакцине")
    public void admissionInfinityForOverdueCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseOverdue(driver);
        smevPage.submitAdmissionInfinite(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusInfinity(driver);
        statusPage.getOverdueStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание бессрочного медотвода для пользователя с не наступившим сертификатом по вакцине")
    public void admissionInfinityForNotArrivedCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseHasNotArrived(driver);
        smevPage.submitAdmissionInfinite(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusInfinity(driver);
        statusPage.getNotArrivedStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание срочного медотвода для пользователя с активным сертификатом по вакцине")
    public void admissionForActiveCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseActive(driver);
        smevPage.submitAdmissionActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusUntil(driver);
        statusPage.getActiveStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание срочного медотвода для пользователя с просроченным сертификатом по вакцине")
    public void admissionForOverdueCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseOverdue(driver);
        smevPage.submitAdmissionActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusUntil(driver);
        statusPage.getOverdueStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание срочного медотвода для пользователя с не наступившим сертификатом по вакцине")
    public void admissionForNotArrivedCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitVaccineSinglePhaseHasNotArrived(driver);
        smevPage.submitAdmissionActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusUntil(driver);
        statusPage.getNotArrivedStatus(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание более позднего медотвода на уже существующий ранний медотвод")
    public void admissionForLater() throws SQLException, IOException, ParseException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        smevPage.submitAdmissionEarly(driver);
        Date date = smevPage.submitAdmissionActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.checkDateForAdmission(driver, date);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusUntil(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 2);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 2);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 2);
    }

    @Test
    @Feature(value = "Медотвод")
    @Description(value = "Создание более раннего медотвода на уже существующий поздний медотвод")
    public void admissionForEarly() throws SQLException, IOException, ParseException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        Date date = smevPage.submitAdmissionActive(driver);
        smevPage.submitAdmissionEarly(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_TARANTION);
        loginPage.enterPassword(PASS_TARANTINO);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.checkDateForAdmission(driver, date);
        StatusPage statusPage = covidPage.getQRUrl(driver);
        statusPage.getAdmissionStatusUntil(driver, date);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 2);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 2);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 2);
    }

    @Test
    @Feature(value = "МФЦ")
    @Description("Проверка получения сертификата МФЦ")
    public void mfcVaccineActive() throws SQLException, IOException, ParseException {
        Sql sql = new Sql();
        sql.deleteMfcRequestForLastName(ConnectionStands.UAT, LASTNAME_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        driver = start(SMEVUAT);
        SmevPage smevPage = new SmevPage(driver);
        Date date = smevPage.submitVaccineSinglePhaseActive(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_MFC);
        loginPage.enterPassword(PASS_MFC);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(URL_UAT);
        MfcPage mfcPage = mainPage.getMfcPage(driver);
        mfcPage.enterEmployer(driver);
        MfcInformationPage mfcInformationPage = mfcPage.getActiveVaccineCert(driver);
        mfcInformationPage.getTrueStatusPerson(driver);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Получение элн по номеру")
    public void getElnDetailsForNumber(){
        driver = start(URL_UAT);
        MainPage mainPage = new MainPage(driver).getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_ELN);
        loginPage.enterPassword(PASS_ELN);
        mainPage = loginPage.enterClick();
        mainPage.getMainPage(URL_UAT);
        ElnPage elnPage = mainPage.getElnPage(driver);
        ElnDetailsPage elnDetailsPage = elnPage.getElnForNumber(ELN_NUMBER);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Получение элн по периоду")
    public void getElnDetailsForPeriod(){
        driver = start(URL_UAT);
        MainPage mainPage = new MainPage(driver).getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_ELN);
        loginPage.enterPassword(PASS_ELN);
        mainPage = loginPage.enterClick();
        mainPage.getMainPage(URL_UAT);
        ElnPage elnPage = mainPage.getElnPage(driver);
        ElnResultPage elnResultPage = elnPage.getElnFor6Monthes(driver);
        ElnDetailsPage elnDetailsPage = elnResultPage.clickMore(driver);
        elnDetailsPage.checkOpenStatus(driver);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Отсутствие элн за период")
    public void noElnForPeriod(){
        driver = start(URL_UAT);
        MainPage mainPage = new MainPage(driver).getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_ELN);
        loginPage.enterPassword(PASS_ELN);
        mainPage = loginPage.enterClick();
        mainPage.getMainPage(URL_UAT);
        ElnPage elnPage = mainPage.getElnPage(driver);
        ElnResultPage elnResultPage = elnPage.getElnFor1Month(driver);
        elnResultPage.checkNotFound(driver);
    }

    @Test
    @Feature(value = "ЭЛН")
    @Description("Отсутствие элн за выбранный период")
    public void getElnForSelectPeriod(){
        driver = start(URL_UAT);
        MainPage mainPage = new MainPage(driver).getMainPage(URL_UAT);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_ELN);
        loginPage.enterPassword(PASS_ELN);
        mainPage = loginPage.enterClick();
        mainPage.getMainPage(URL_UAT);
        ElnPage elnPage = mainPage.getElnPage(driver);
        ElnResultPage elnResultPage = elnPage.getElnForNewPeriod(driver);
        elnResultPage.checkNotFound(driver);
    }
}
