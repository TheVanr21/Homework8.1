package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.mode.User;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

    private final static String clearCardTransactionsQuery = "DELETE FROM card_transactions";
    private final static String clearCardQuery = "DELETE FROM cards";
    private final static String clearAuthCodesQuery = "DELETE FROM auth_codes";
    private final static String clearUsersQuery = "DELETE FROM users";
    private final static String getUserQuery = "SELECT * FROM users WHERE login = ?";
    private final static String getAuthCodeQuery = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1";
    private static QueryRunner runner = new QueryRunner();

    public static void clearDataBase(DataHelper.DataBase db) {
        try (
                Connection connection = DriverManager.getConnection(db.getUrl(), db.getLogin(), db.getPassword())
        ) {
            runner.update(connection, clearCardTransactionsQuery);
            runner.update(connection, clearCardQuery);
            runner.update(connection, clearAuthCodesQuery);
            runner.update(connection, clearUsersQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static User getUserFromBD(DataHelper.DataBase db, String login) {
        User user = null;

        try (
                Connection connection = DriverManager.getConnection(db.getUrl(), db.getLogin(), db.getPassword())
        ) {
            user = runner.query(connection, getUserQuery, login, new BeanHandler<>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            user.setPassword("qwerty123");
        }

        return user;
    }

    public static String getVerificationCode(DataHelper.DataBase db, User user) {
        String verificationCode = null;

        try (
                Connection connection = DriverManager.getConnection(db.getUrl(), db.getLogin(), db.getPassword())
        ) {
            verificationCode = runner.query(connection, getAuthCodeQuery, user.getId(), new ScalarHandler<>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return verificationCode;
    }
}
