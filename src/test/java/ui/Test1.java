package ui;

import org.junit.Test;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import ui.pages.CovidPage;
import ui.pages.LoginPage;
import ui.pages.MainPage;
import ui.sql.ConnectionStands;
import ui.sql.Sql;

import java.io.IOException;
import java.sql.*;

public class Test1 extends BaseTest{
    WebDriver driver;

    @Test
    public void loginTest() throws NotFoundException, IOException, SQLException {
        driver = start(url_uat);
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.enter(driver);
        loginPage.enterUserName(LOGIN_KUBRIK);
        loginPage.enterPassword(PASS_KUBRIK);
        mainPage = loginPage.enterClick();
        mainPage = mainPage.getMainPage(url_uat);
        CovidPage covidPage = mainPage.getCovidPage(driver);
        covidPage.openQr(driver);
        setDown(driver);
        Sql sql = new Sql();
        //Boolean exe = sql.selectVcUserForOid(ConnectionStands.DEV, oid_Kubrik);
       // System.out.println(exe);
    }

    @Test
    public void bdTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://10.81.8.31:5432/vaccine?prepareThreshold=0", "vaccine", "vaccine");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from vc_user where user_id = '1000542025';");
        // Количество колонок в результирующем запросе
        int columns = rs.getMetaData().getColumnCount();
        // Перебор строк с данными
        while(rs.next()){
            for (int i = 1; i <= columns; i++){
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
        System.out.println();
        if(rs != null)
            rs.close();
        if(statement != null)
            statement.close();
        if(connection != null)
            connection.close();
    }


}
