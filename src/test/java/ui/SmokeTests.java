package ui;

import io.qameta.allure.Description;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import ui.sql.ConnectionStands;
import ui.sql.Sql;

import java.sql.*;
import java.util.ArrayList;

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
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, "state", 1);
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
        statusPage.getActiveStatus();
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, "state", 1);
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
        statusPage.getHasNotArriveStatus();
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, "state", 1);
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
        statusPage.getActiveStatus();
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, "state", 2);
    }

    @Test
    @Description("Проверка истекшей двухфазной вакцины")
    public void vaccineTwoPhasesOverdue() throws SQLException {
        Sql sql = new Sql();
        try {
            sql.prepareForTestVaccine(ConnectionStands.UAT, OID_TARANTINO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        statusPage.getNegativeStatus();
        setDown(driver);
        sql.checkInVcUser(ConnectionStands.UAT, OID_TARANTINO, "state", 2);
    }
}
