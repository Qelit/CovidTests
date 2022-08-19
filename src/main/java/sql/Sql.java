package sql;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import sql.ConnectionStands;

import java.sql.*;
import java.util.ArrayList;

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
    protected String uat_url_oracle = "jdbc:oracle:thin:@10.81.8.36:1521:u00pgu";
    protected String uat_login_oracle = "pgu";
    protected String uat_password_oracle = "pgu";
    protected String dev_url_oracle = "pgu@//10.81.21.31:1521/u00pgu";
    protected String dev_login_oracle = "pgu";
    protected String dev_password_oracle = "pgu";
    protected String dev2_url_oracle = "pgu@//10.81.21.87:1521/u00pgu";
    protected String dev2_login_oracle = "pgu";
    protected String dev2_password_oracle = "pgu";
    final String confirm = "confirm";

    Connection connection;
    protected Logger logger = LogManager.getLogger();

    public Sql(){}

    //Возвращает true или false по запросу из vc_user по oid
    public Boolean selectVcUserForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        Boolean exe = true;
        try {
            statement = getConnection(connectionStands);
            exe = statement.execute("select * from vc_user where user_id = '" + oid + "';");
            // Закрытие соединения
            connection.close();
            return exe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exe;
    }

    public Sql checkInVcUser(ConnectionStands connectionStands, String oid, String columnName, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select " + columnName + " from vc_user where user_id = '" + oid + "';");
            // Закрытие соединения
            int lines = checkTable(rs, columnName, "CONFIRM");
            Assert.assertTrue(lines == count);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    public void checkInCovidStatusCertAntibodies(ConnectionStands connectionStands, String oid)  {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select unrz_antibodies from covid_status_cert where oid = '" + oid + "';");
            while(rs.next()){
                Assert.assertTrue(rs.getString("unrz_antibodies") == null);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Sql checkInCovidRegisterRecord(ConnectionStands connectionStands, String oid, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select * from covid_register_record where user_id = '" + oid + "'; ");
            checkTable(rs, count);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    @Step("Проверка количества строк выдаваемых при запросе")
    private void checkTable(ResultSet rs, int count) {
        int rowCount = 0;
        if(rs!=null) {
            try {
                rs.last();
                rowCount = rs.getRow();
                Assert.assertTrue(rowCount == count);
            } catch (SQLException e) {
                if (connection != null)
                    try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Step("Проверка записей в бд admission")
    public Sql checkInAdmission(ConnectionStands connectionStands, String oid, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select * from admission where oid = '"+ oid +"';");
            checkTable(rs, count);
        } catch (SQLException e) {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new Sql();
    }

    @Step("Проверка записей в бд admission_request")
    public Sql checkInAdmissionRequest(ConnectionStands connectionStands, String oid, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select * from admission_request where user_id = '"+ oid +"';");
            checkTable(rs, count);
        } catch (SQLException e) {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new Sql();
    }

    public Sql checkInAdmissionResponse(ConnectionStands connectionStands, String oid, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select * from admission_response where oid = '"+ oid +"';");
            checkTable(rs, count);
        } catch (SQLException e) {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new Sql();
    }

    @Step("Проверка количества строк в таблице vc_cert")
    public Sql checkInVcCert(ConnectionStands connectionStands, String oid, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select * from vc_cert where user_id ='"+ oid +"';");
            int rowCount = 0;
            if(rs!=null) {
                rs.last();
                rowCount = rs.getRow();
            }
            Assert.assertTrue(rowCount == count);
        } catch (SQLException e) {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new Sql();
    }

    @Step("Проверка количества строк в таблице covid_status_cert")
    public Sql checkInCovidStatusCert(ConnectionStands connectionStands, String oid, int count) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            ResultSet rs = statement.executeQuery("select * from covid_status_cert where oid ='"+ oid +"';");
            int rowCount = 0;
            if(rs!=null) {
                rs.last();
                rowCount = rs.getRow();
            }
            Assert.assertTrue(rowCount == count);
        } catch (SQLException e) {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new Sql();
    }

    @Step("Проверка наличия значения {verifiable} в названном столбце {columnName}")
    private int checkTable(ResultSet rs, String columnName, String verifiable) {
        ArrayList<String> result = new ArrayList<>();
        // Перебор строк с данными
        while (true) {
            try {
                while (rs.next()) {
                    result.add(rs.getString(columnName));
                }
                for (int i = 0; i < result.size(); i++) {
                    Assert.assertTrue(result.get(i).contains(verifiable));
                }
                return result.size();
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    //Возвращает true или false по запросу из vc_cert по oid
    public Boolean selectVcCertForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        Boolean exe = false;
        try {
            statement = getConnection(connectionStands);
            exe = statement.execute("select * from vc_cert where user_id = " + oid + ";");
            // Закрытие соединения
            connection.close();
            return exe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exe;
    }

    //Возвращает true или false по запросу из covid_status_cert по oid
    public Boolean selectCovidStatusCertForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        Boolean exe = false;
        try {
            statement = getConnection(connectionStands);
            exe = statement.execute("select * from covid_status_cert where oid = " + oid);
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exe;
    }

    //Возвращает true или false по запросу из covid_register_record по oid
    public Boolean selectCovidRegisterRecordForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        Boolean exe = false;
        try {
            statement = getConnection(connectionStands);
            exe = statement.execute("select * from covid_register_record where user_id = '" + oid + "';");
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exe;
    }

    //Возвращает true или false по запросу из covid_test_from_mo oracle по oid
    public Boolean selectCovidTestFromMoForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        Boolean exe = false;
        try {
            statement = getConnection(connectionStands);
            exe = statement.execute("SELECT * FROM LK.COVID_TEST_FROM_MO_DP where oid = " + oid);
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exe;
    }

    @Step("удаляет записи из бд vc_cert по oid")
    public void deleteVcCertForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("DELETE from vc_cert where user_id = '" + oid + "';");
            // Закрытие соединения
            connection.close();
            if (upd > 0)
                logger.info("Количество удаленых строк = " + upd);
            else logger.info("Не найдены подходящие записи для удаления");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Step("удаляет записи из бд vc_user по oid")
    public void deleteVcUserForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("DELETE from vc_user where user_id = '" + oid + "';");
            // Закрытие соединения
            connection.close();
            if (upd > 0)
                logger.info("Количество удаленых строк = " + upd);
            else logger.info("Не найдены подходящие записи для удаления");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Step("удаляет записи из бд covid_register_record по oid")
    public void deleteCovidRegisterRecordForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("DELETE from covid_register_record where user_id = '" + oid + "';");
            // Закрытие соединения
            connection.close();
            if (upd > 0)
                logger.info("Количество удаленых строк = " + upd);
            else logger.info("Не найдены подходящие записи для удаления");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Step("Удаление записи из бд mfc_request по фамилии")
    public Sql deleteMfcRequestForLastName(ConnectionStands connectionStands, String lastName) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("delete from mfc_request where last_name like '" + lastName + "';");
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    @Step("удаляет записи из бд covid_status_cert по oid")
    public Sql deleteCovidStatusCertForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("delete from  covid_status_cert where oid =  " + oid);
            // Закрытие соединения
            connection.close();
            if (upd > 0)
                logger.info("Количество удаленых строк = " + upd);
            else logger.info("Не найдены подходящие записи для удаления");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    @Step("Удаление записи из бд admission по oid")
    public void deleteAdmissionForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("delete from admission where oid =  " + oid);
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Step("Удаление записи из бд admission_request по oid")
    public void deleteAdmissionRequestForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("delete from admission_request where user_id = " + oid);
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Step("Удаление записи из бд admission_response по oid")
    public void deleteAdmissionResponseForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int upd = statement.executeUpdate("delete from admission_response where oid = " + oid);
            // Закрытие соединения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Step("Удаление из бд covid_status_cert, vc_user и vc_cert полей по oid")
    public Sql prepareForTestVaccine(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getConnection(connectionStands);
            int delCVC = statement.executeUpdate("delete from covid_status_cert where oid =  " + oid);
            int delVCS = statement.executeUpdate("DELETE from vc_user where user_id = '" + oid + "';");
            int delVCU = statement.executeUpdate("DELETE from vc_cert where user_id = '" + oid + "';");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    @Step("Удаление записей из бд covid_register_record и covid_status_cert")
    public Sql prepareForTestIllness(ConnectionStands connectionStands, String oid) throws SQLException {
        deleteCovidRegisterRecordForOid(connectionStands, oid);
        deleteCovidStatusCertForOid(connectionStands, oid);
        return new Sql();
    }

    @Step("Подготовка к тестированию медотводов")
    public Sql prepareForTestAdmission(ConnectionStands connectionStands, String oid) throws SQLException {
        deleteCovidStatusCertForOid(connectionStands, oid);
        deleteAdmissionForOid(connectionStands, oid);
        deleteAdmissionResponseForOid(connectionStands, oid);
        deleteAdmissionRequestForOid(connectionStands, oid);
        return new Sql();
    }

    @Step("Удаление записей из бд LK.COVID_TEST_FROM_MO по СНИЛС")
    public Sql deleteCovidTestFromMoForSnils(ConnectionStands connectionStands, String snils) {
        Statement statement = null;
        try {
            statement = getOracleConnection(connectionStands);
            int del = statement.executeUpdate("delete from LK.COVID_TEST_FROM_MO where snils ='" + snils + "'");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    @Step("Удаление записей из бд LK.COVID_TEST_FROM_MO_DP по oid")
    public Sql deleteCovidTestFromMoDpForOid(ConnectionStands connectionStands, String oid) {
        Statement statement = null;
        try {
            statement = getOracleConnection(connectionStands);
            int del = statement.executeUpdate("delete from LK.COVID_TEST_FROM_MO_DP where oid ='" + oid + "'");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Sql();
    }

    @Step("Получение подключения по стенду для postgreSQL")
    private Statement getConnection(ConnectionStands connectionStands) throws SQLException {
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
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement;
    }

    @Step("Получение подключения по стенду для Oracle")
    private Statement getOracleConnection(ConnectionStands connectionStands) throws SQLException {
        switch(connectionStands){
            case UAT:
                connection = DriverManager.getConnection(uat_url_oracle, uat_login_oracle, uat_password_oracle);
                break;
            case DEV:
                connection = DriverManager.getConnection(dev_url_oracle, dev_login_oracle, dev_password_oracle);
                break;
            case DEV2:
                connection = DriverManager.getConnection(dev2_url_oracle, dev_login_oracle, dev_password_oracle);
                break;
        }
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement;
    }

}