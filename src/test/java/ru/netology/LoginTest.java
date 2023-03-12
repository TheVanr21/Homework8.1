package ru.netology;

import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.mode.User;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    DataHelper.DataBase db;
    String login;

    @BeforeEach
    public void init() {
        db = DataHelper.getDataBaseConnection();
        login = DataHelper.getValidLogin();
    }

    @AfterAll
    public static void clearDb() {
        DBHelper.clearDataBase(DataHelper.getDataBaseConnection());
    }


    @Test
    @DisplayName("Should successfully login")
    public void shouldSuccessfullyLogin() {
        User user = DBHelper.getUserFromBD(db, login);
        if (user == null) Assertions.fail("User '" + login + "' not found");

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.login(user, null, null);

        String verificationCode = DBHelper.getVerificationCode(db, user);
        if (verificationCode == null) Assertions.fail("There is no validation codes for user " + login);

        verificationPage.verify(verificationCode, false, false);
    }

    @Test
    @DisplayName("Should not login with wrong verification code")
    public void shouldNotLoginVerification() {
        User user = DBHelper.getUserFromBD(db, login);
        if (user == null) Assertions.fail("User '" + login + "' not found");

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.login(user, null, null);

        String verificationCode = DBHelper.getVerificationCode(db, user);
        if (verificationCode == null) Assertions.fail("There is no validation codes for user " + login);

        String wrongVerificationCode = DataHelper.getWrongVerificationCode(verificationCode);
        verificationPage.verify(wrongVerificationCode, true, false);
    }

    @Test
    @Disabled
    @DisplayName("Should display error on 4th wrong code")
    public void shouldDisplayErrorVerificationOnFourthTime() {
        shouldNotLoginVerification();
        shouldNotLoginVerification();
        shouldNotLoginVerification();

        User user = DBHelper.getUserFromBD(db, login);
        if (user == null) Assertions.fail("User '" + login + "' not found");

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.login(user, null, null);

        String verificationCode = DBHelper.getVerificationCode(db, user);
        if (verificationCode == null) Assertions.fail("There is no validation codes for user " + login);

        String wrongVerificationCode = DataHelper.getWrongVerificationCode(verificationCode);
        verificationPage.verify(wrongVerificationCode, true, true);
    }

    @Test
    @DisplayName("Should not login with wrong password")
    public void shouldNotLoginWrongPass() {
        User user = DBHelper.getUserFromBD(db, login);
        if (user == null) Assertions.fail("User '" + login + "' not found");

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        String wrongPassword = DataHelper.getWrongPassword(user.getPassword());
        loginPage.login(user, null, wrongPassword);
    }

    @Test
    @DisplayName("Should not login with wrong login")
    public void shouldNotLoginWrongLogin() {
        User user = DBHelper.getUserFromBD(db, login);
        if (user == null) Assertions.fail("User '" + login + "' not found");

        open("http://localhost:9999/");
        LoginPage loginPage = new LoginPage();
        String wrongLogin = DataHelper.getWrongLogin(user.getLogin());
        loginPage.login(user, wrongLogin, null);
    }
}