package com.unlimint.drd.base;

import com.unlimint.drd.data.Locators;
import com.unlimint.drd.data.URLs;
import com.unlimint.drd.steps.ui.ChargebacksReport;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public abstract class UiSteps<T extends UiSteps<?>> {
    protected final Class<T> returnedType;

    protected UiSteps(Class<T> type) {
        this.returnedType = type;
    }

    @Step("Login to DRD")
    public T doLogin(String login, String password) {
        open(URLs.getDRD());

        $(Locators.getUSER_NAME_TXB())
                .as("Username textbox")
                .setValue(login);

        $(Locators.getPASSWORD_TXB())
                .as("Password textbox")
                .setValue(password);

        $(Locators.getSIGN_IN_BTN())
                .as("Sign In button")
                .click();

        return page(returnedType);
    }

    @Step("Click on Chb Reports button")
    public ChargebacksReport goToChbReports() {
        $(Locators.getCHB_REPORTS_BTN())
                .as("Chb Reports button")
                .click();

        return page(ChargebacksReport.class);
    }

    @Step("Log-out")
    public void doLogout() {
        $(Locators.getUSER_DROPDOWN())
                .as("User dropdown")
                .click();

        $(Locators.getLOGOUT_BTN())
                .as("Log-out button")
                .click();
    }
}
