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

    public DashboardPage verify(String verificationCode, boolean isWrong, boolean isFourth) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        if (isWrong && !isFourth) {
            errorMessage.shouldHave(Condition.text("Неверно указан код! Попробуйте ещё раз.")).shouldBe(Condition.visible);
            return null;
        }
        if (isWrong && isFourth) {
            errorMessage.shouldHave(Condition.text("Превышено количество попыток ввода кода!")).shouldBe(Condition.visible);
            return null;
        }
        return new DashboardPage();
    }

    public void isOpen() {
        codeField.shouldBe(Condition.visible);
    }
}
