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

    public VerificationPage validLogin(User user) {
        loginField.setValue(user.getLogin());
        passwordField.setValue(user.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void notValidLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        errorMessage.shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
