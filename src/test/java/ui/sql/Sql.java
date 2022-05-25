package ui.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql {

    protected String uat_url = "jdbc:postgresql://10.81.8.31:5432/vaccine?prepareThreshold=0";
    protected String uat_login = "vaccine";
    protected String uat_password = "vaccine";
    protected String dev_url = "jdbc:postgresql://10.81.21.24/vaccine?prepareThreshold=0";
    protected String dev_login = "vaccine";
    protected String dev_password = "thai6aShud";
    protected String dev2_url = "jdbc:postgresql://10.81.21.86/vaccine?prepareThreshold=0";
    protected String dev2_login = "vaccine";
    protected String dev2_password = "thai6aShud";
    protected String uat_url_oracle = "pgu@//10.81.8.36:1521/u00pgu";
    protected String uat_login_oracle = "pgu";
    protected String uat_password_oracle = "pgu";
    protected String dev_url_oracle = "pgu@//10.81.21.31:1521/u00pgu";
    protected String dev_login_oracle = "pgu";
    protected String dev_password_oracle = "pgu";
    protected String dev2_url_oracle = "pgu@//10.81.21.87:1521/u00pgu";
    protected String dev2_login_oracle = "pgu";
    protected String dev2_password_oracle = "pgu";

    Connection connection;
    protected Logger logger = LogManager.getLogger();

    public Sql(){

    }

    //Возвращает true или false по запросу из vc_user по oid
    public Boolean selectVcUserForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
            connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        Boolean exe = statement.execute("select * from vc_user where user_id = '" + oid + "';");
        // Закрытие соединения
        connection.close();
        return exe;
    }

    //Возвращает true или false по запросу из vc_cert по oid
    public Boolean selectVcCertForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        Boolean exe = statement.execute("select * from vc_cert where user_id = " + oid + ";");
        // Закрытие соединения
        connection.close();
        return exe;
    }

    //Возвращает true или false по запросу из covid_status_cert по oid
    public Boolean selectCovidStatusCertForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        Boolean exe = statement.execute("select * from covid_status_cert where oid = " + oid);
        // Закрытие соединения
        connection.close();
        return exe;
    }

    //Возвращает true или false по запросу из covid_register_record по oid
    public Boolean selectCovidRegisterRecordForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        Boolean exe = statement.execute("select * from covid_register_record where user_id = '" + oid + "';");
        // Закрытие соединения
        connection.close();
        return exe;
    }

    //Возвращает true или false по запросу из covid_test_from_mo oracle по oid
    public Boolean selectCovidTestFromMoForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url_oracle, uat_login_oracle, uat_password_oracle);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url_oracle, dev_login_oracle, dev_password_oracle);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url_oracle, dev2_login_oracle, dev2_password_oracle);
                break;
        }
        Statement statement = connection.createStatement();
        Boolean exe = statement.execute("SELECT * FROM LK.COVID_TEST_FROM_MO_DP where oid = " + oid);
        // Закрытие соединения
        connection.close();
        return exe;
    }

    //удаляет записи из бд vc_cert по oid
    public void deleteVcCertForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        int upd = statement.executeUpdate("DELETE from vc_cert where user_id = '" + oid + "';");
        // Закрытие соединения
        connection.close();
        if (upd > 0)
            logger.info("Количество удаленых строк = " + upd);
         else logger.info("Не найдены подходящие записи для удаления");
    }

    //удаляет записи из бд vc_user по oid
    public void deleteVcUserForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        int upd = statement.executeUpdate("DELETE from vc_user where user_id = '" + oid + "';");
        // Закрытие соединения
        connection.close();
        if (upd > 0)
            logger.info("Количество удаленых строк = " + upd);
        else logger.info("Не найдены подходящие записи для удаления");
    }

    //удаляет записи из бд covid_register_record по oid
    public void deleteCovidRegisterRecordForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        int upd = statement.executeUpdate("DELETE from covid_register_record where user_id = '" + oid + "';");
        // Закрытие соединения
        connection.close();
        if (upd > 0)
            logger.info("Количество удаленых строк = " + upd);
        else logger.info("Не найдены подходящие записи для удаления");
    }

    //удаляет записи из бд covid_status_cert по oid
    public void deleteCovidStatusCertForOid(ConnectionStands connectionStands, String oid) throws SQLException {
        switch(connectionStands) {
            case UAT:
                connection = DriverManager.getConnection(uat_url, uat_login, uat_password);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url, dev_login, dev_password);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url, dev2_login, dev2_password);
                break;
        }
        Statement statement = connection.createStatement();
        int upd = statement.executeUpdate("delete from  covid_status_cert where oid =  " + oid);
        // Закрытие соединения
        connection.close();
        if (upd > 0)
            logger.info("Количество удаленых строк = " + upd);
        else logger.info("Не найдены подходящие записи для удаления");
    }


}

