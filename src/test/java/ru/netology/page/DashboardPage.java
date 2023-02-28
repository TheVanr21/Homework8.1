package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement dashboardHeader = $("[data-test-id='dashboard']");

    public DashboardPage() {
        isOpen();
    }

    public void isOpen() {
        dashboardHeader.shouldBe(Condition.visible);
    }
}
