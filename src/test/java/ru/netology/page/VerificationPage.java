package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage() {
        isOpen();
    }

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void notValidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        errorMessage.shouldHave(Condition.text("Неверно указан код! Попробуйте ещё раз.")).shouldBe(Condition.visible);
    }

    public void notValidVerifyFourth(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        errorMessage.shouldHave(Condition.text("Превышено количество попыток ввода кода!")).shouldBe(Condition.visible);
    }

    public void isOpen() {
        codeField.shouldBe(Condition.visible);
    }
}
