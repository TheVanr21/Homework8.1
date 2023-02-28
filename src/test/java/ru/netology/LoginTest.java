package ru.netology;

import com.github.javafaker.Faker;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.*;
import ru.netology.mode.User;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    static String[] db = {"jdbc:mysql://localhost:3306/app", "app", "pass"};

    @AfterAll
    public static void clearDb() {
        String clearCardTransactions =  "DELETE FROM card_transactions";
        String clearCard =  "DELETE FROM cards";
        String clearAuthCodes =  "DELETE FROM auth_codes";
        String clearUsers =  "DELETE FROM users";
        QueryRunner runner = new QueryRunner();
        try (
                Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);
        ) {
            runner.update(connection, clearCardTransactions);
            runner.update(connection, clearCard);
            runner.update(connection, clearAuthCodes);
            runner.update(connection, clearUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Should successfully login")
    public void shouldSuccessfullyLogin() {
        QueryRunner runner = new QueryRunner();
        String getUser = "SELECT * FROM users WHERE login = 'vasya'";
        User user = null;

        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            user = runner.query(connection, getUser, new BeanHandler<>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user!=null){
            user.setPassword("qwerty123");
        } else {
            System.out.println("User 'vasya' not found");
            Assertions.fail();
        }

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(user);

        String getAuthCode = "SELECT code FROM auth_codes WHERE user_id = '" + user.getId() + "' ORDER BY created DESC LIMIT 1";
        String verificationCode = null;
        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            verificationCode = runner.query(connection, getAuthCode, new ScalarHandler<>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (verificationCode==null){
            System.out.println("There is no validation codes for user");
            Assertions.fail();
        }

        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Should not login with wrong verification code")
    public void shouldNotLoginVerification() {
        Faker faker = new Faker();
        QueryRunner runner = new QueryRunner();
        String getUser = "SELECT * FROM users WHERE login = 'vasya'";
        User user = null;

        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            user = runner.query(connection, getUser, new BeanHandler<>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user!=null){
            user.setPassword("qwerty123");
        } else {
            System.out.println("User 'vasya' not found");
            Assertions.fail();
        }

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(user);

        String getAuthCode = "SELECT code FROM auth_codes WHERE user_id = '" + user.getId() + "' ORDER BY created DESC LIMIT 1";
        String verificationCode = null;
        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            verificationCode = runner.query(connection, getAuthCode, new ScalarHandler<>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (verificationCode==null){
            System.out.println("There is no validation codes for user");
            Assertions.fail();
        }

        String wrongVerificationCode = verificationCode;
        while (wrongVerificationCode.equals(verificationCode)) {
            wrongVerificationCode = faker.numerify("######");
        }

        verificationPage.notValidVerify(wrongVerificationCode);
    }

    @Test
    @Disabled
    @DisplayName("Should display error on 4th wrong code")
    public void shouldDisplayErrorVerificationOnFourthTime() {
        shouldNotLoginVerification();
        shouldNotLoginVerification();
        shouldNotLoginVerification();

        Faker faker = new Faker();
        QueryRunner runner = new QueryRunner();
        String getUser = "SELECT * FROM users WHERE login = 'vasya'";
        User user = null;

        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            user = runner.query(connection, getUser, new BeanHandler<>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user!=null){
            user.setPassword("qwerty123");
        } else {
            System.out.println("User 'vasya' not found");
            Assertions.fail();
        }

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(user);

        String getAuthCode = "SELECT code FROM auth_codes WHERE user_id = '" + user.getId() + "' ORDER BY created DESC LIMIT 1";
        String verificationCode = null;
        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            verificationCode = runner.query(connection, getAuthCode, new ScalarHandler<>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (verificationCode==null){
            System.out.println("There is no validation codes for user");
            Assertions.fail();
        }

        String wrongVerificationCode = verificationCode;
        while (wrongVerificationCode.equals(verificationCode)) {
            wrongVerificationCode = faker.numerify("######");
        }

        verificationPage.notValidVerifyFourth(wrongVerificationCode);
    }

    @Test
    @DisplayName("Should not login with wrong password")
    public void shouldNotLoginWrongPass() {
        Faker faker = new Faker();
        QueryRunner runner = new QueryRunner();
        String getUser = "SELECT * FROM users WHERE login = 'vasya'";
        User user = null;

        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            user = runner.query(connection, getUser, new BeanHandler<>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user!=null){
            user.setPassword("qwerty123");
        } else {
            System.out.println("User 'vasya' not found");
            Assertions.fail();
        }

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        loginPage.notValidLogin(user.getLogin(), faker.internet().password());
    }

    @Test
    @DisplayName("Should not login with wrong login")
    public void shouldNotLoginWrongLogin() {
        Faker faker = new Faker();
        QueryRunner runner = new QueryRunner();
        String getUser = "SELECT * FROM users WHERE login = 'vasya'";
        User user = null;

        try (Connection connection = DriverManager.getConnection(db[0], db[1], db[2]);) {
            user = runner.query(connection, getUser, new BeanHandler<>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user!=null){
            user.setPassword("qwerty123");
        } else {
            System.out.println("User 'vasya' not found");
            Assertions.fail();
        }

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        loginPage.notValidLogin(faker.name().firstName(), user.getPassword());
    }

}
