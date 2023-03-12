package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {

    @Value
    public static class DataBase {
        private String url;
        private String login;
        private String password;
    }

    private static Faker faker = new Faker();

    public static DataBase getDataBaseConnection() {
        return new DataBase("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static String getValidLogin() {
        return "vasya";
    }

    public static String getWrongVerificationCode(String verificationCode) {
        String wrongVerificationCode = verificationCode;
        while (wrongVerificationCode.equals(verificationCode)) {
            wrongVerificationCode = faker.numerify("######");
        }
        return wrongVerificationCode;
    }

    public static String getWrongLogin(String login) {
        String wrongLogin = login;
        while (wrongLogin.equals(login)) {
            wrongLogin = faker.name().firstName();
        }
        return wrongLogin;
    }

    public static String getWrongPassword(String password) {
        String wrongPassword = password;
        while (wrongPassword.equals(password)) {
            wrongPassword = faker.internet().password();
        }
        return wrongPassword;
    }
}
