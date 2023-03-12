package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.mode.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage login(User user, String wrongLogin, String wrongPassword) {
        if (wrongLogin == null) {
            loginField.setValue(user.getLogin());
        } else {
            loginField.setValue(wrongLogin);
        }
        if (wrongPassword == null) {
            passwordField.setValue(user.getPassword());
        } else {
            passwordField.setValue(wrongPassword);
        }
        loginButton.click();
        if (wrongLogin != null || wrongPassword != null) {
            errorMessage.shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
            return null;
        }
        return new VerificationPage();
    }
}
