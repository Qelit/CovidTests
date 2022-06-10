package ui;

import io.qameta.allure.Description;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import ui.sql.ConnectionStands;
import ui.sql.Sql;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public class SmokeTests extends BaseTest{
    WebDriver driver;

    @Test
    @Description(value = "Отсутствует вакцина")
    public void vaccineEmpty() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
    }


    @Test
    @Description(value = "Вакцинация первым компонентом двухфазной вакцины")
    public void vaccineFirstPhase() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 0);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 0);
    }

    @Test
    @Description(value = "Проверка активной однофазной вакцины")
    public void vaccineSingleActive() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Проверка однофазной вакцины, срок которой не наступил")
    public void vaccineSingleHasNotArrived() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Проверка просроченной однофазной вакцины")
    public void vaccineSingleOverdue() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 1);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description("Проверка актуальной двухфазной вакцины")
    public void vaccineTwoPhasesActive() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 2);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description("Проверка истекшей двухфазной вакцины")
    public void vaccineTwoPhasesOverdue() throws SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, STATE, 2);
        sql.checkInVcCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description("Проверка ревакцинации при добавлении первой фазы вакцины")
    public void revaccineFirstPhase() throws SQLException, ParseException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
    }

    @Test
    @Description(value = "Ревакцинация двухфазной вакциной")
    public void revaccineTwoPhases() throws ParseException, SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
    }

    @Test
    @Description(value = "Ревацинация однокомпонентой вакциной")
    public void revaccineSinglePhase() throws ParseException, SQLException {
        Sql sql = new Sql();
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
    }

    @Test
    @Description(value = "Проверка активного сертификата по переболезни")
    public void illnessActive() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInCovidRegisterRecord(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Проверка просроченного сертификата по переболезни")
    public void illnessOverdue() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInCovidRegisterRecord(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description("Добавление переболезни к просроченной переболезни")
    public void reIllness() throws SQLException, IOException, ParseException {
        Sql sql = new Sql();
        sql.prepareForTestIllness(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
    }

    @Test
    @Description("Создание срочного медотвода для пользователя с отсутствующим сертификатом")
    public void admissionForNoCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание бессрочного медотвода для пользователей с отсутствующим сертификатом")
    public void admissionInfinityForNoCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание бессрочного медотвода для пользователя с активным сертификатом по вакцине")
    public void admissionInfinityForActiveCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание бессрочного медотвода для пользователя с просроченным сертификатом по вакцине")
    public void admissionInfinityForOverdueCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание бессрочного медотвода для пользователя с не наступившим сертификатом по вакцине")
    public void admissionInfinityForNotArrivedCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание срочного медотвода для пользователя с активным сертификатом по вакцине")
    public void admissionForActiveCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание срочного медотвода для пользователя с просроченным сертификатом по вакцине")
    public void admissionForOverdueCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }

    @Test
    @Description(value = "Создание срочного медотвода для пользователя с не наступившим сертификатом по вакцине")
    public void admissionForNotArrivedCert() throws SQLException, IOException {
        Sql sql = new Sql();
        sql.prepareForTestAdmission(ConnectionStands.UAT, OID_TARANTINO);
        sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
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
        setDown(driver);
        sql.checkInAdmission(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInCovidStatusCert(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionRequest(ConnectionStands.UAT, OID_TARANTINO, 1);
        sql.checkInAdmissionResponse(ConnectionStands.UAT, OID_TARANTINO, 1);
    }
}
