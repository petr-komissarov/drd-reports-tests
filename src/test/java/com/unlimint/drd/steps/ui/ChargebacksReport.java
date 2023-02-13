package com.unlimint.drd.steps.ui;

import com.unlimint.drd.base.UiSteps;
import com.unlimint.drd.data.Locators;
import com.unlimint.drd.helpers.LocalDates;
import io.qameta.allure.Step;
import org.opentest4j.AssertionFailedError;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ChargebacksReport extends UiSteps<ChargebacksReport> {
    private static final String STRING_DATE_FORMAT = "MMMM dd, yyyy";

    public ChargebacksReport() {
        super(ChargebacksReport.class);
    }

    @Step("Select CheckBoxes")
    public ChargebacksReport selectCheckBoxes(boolean isResolvedChargebacks,
                                              boolean isAccepted,
                                              boolean isWon,
                                              boolean isLost,
                                              boolean isActiveChargebacks,
                                              boolean isMerchants) {
        if (isResolvedChargebacks) {
            $(Locators.getRESOLVED_CHARGEBACKS_CBX())
                    .as("Resolved chargebacks checkbox")
                    .click();
        }

        if (isAccepted) {
            $(Locators.getACCEPTED_CBX())
                    .as("Accepted checkbox")
                    .click();
        }

        if (isWon) {
            $(Locators.getWON_CBX())
                    .as("Won checkbox")
                    .click();
        }

        if (isLost) {
            $(Locators.getLOST_CBX())
                    .as("Lost checkbox")
                    .click();
        }

        if (isActiveChargebacks) {
            $(Locators.getACTIVE_CHARGEBACKS_CBX())
                    .as("Active chargebacks checkbox")
                    .click();
        }

        if (isMerchants) {
            $(Locators.getMERCHANTS_CBX())
                    .as("Merchants checkbox")
                    .click();
        }

        return this;
    }

    @Step("Set Resolved chargebacks period")
    public ChargebacksReport setResolvedChargebacksPeriod(LocalDate newFrom, LocalDate newTo) {
        if (newFrom.isAfter(newTo)) {
            throw new AssertionFailedError("The end date of a Resolved chargebacks period should be >= the start date of a period");
        }

        LocalDates localDates = new LocalDates();

        if (!localDates.getFromElementText(Locators.getFROM_RESOLVED_CHARGEBACKS_TXB(), STRING_DATE_FORMAT).isEqual(newFrom)) {
            new DatePicker(Locators.getFROM_RESOLVED_CHARGEBACKS_TXB()).setDate(newFrom);
        }

        if (!localDates.getFromElementText(Locators.getTO_RESOLVED_CHARGEBACKS_TXB(), STRING_DATE_FORMAT).isEqual(newTo)) {
            new DatePicker(Locators.getTO_RESOLVED_CHARGEBACKS_TXB()).setDate(newTo);
        }

        return this;
    }

    @Step("Set From active chargebacks")
    public ChargebacksReport setFromActiveChargebacks(LocalDate newFrom) {
        if (new LocalDates().getFromElementText(Locators.getFROM_ACTIVE_CHARGEBACKS_TXB(), STRING_DATE_FORMAT).isEqual(newFrom)) {
            return this;
        }

        new DatePicker(Locators.getFROM_ACTIVE_CHARGEBACKS_TXB()).setDate(newFrom);
        return this;
    }

    @Step("Click on Create button")
    public ChargebacksReport doCreateReport() {
        $(Locators.getCREATE_REPORT_BTN())
                .as("Create button")
                .click();

        return this;
    }

    @Step("Wait while a notification with a text Please wait while report is sent to your email... is showing")
    public ChargebacksReport waitReportIsSentToEmail() {
        $(Locators.getPLEASE_WAIT_WHILE_REPORT_IS_SENT_TO_YOUR_EMAIL_TXT())
                .as("Please wait while report is sent to your email... notification")
                .shouldBe(visible);

        $(Locators.getPLEASE_WAIT_WHILE_REPORT_IS_SENT_TO_YOUR_EMAIL_TXT())
                .as("Please wait while report is sent to your email... notification")
                .shouldNotBe(visible);

        return this;
    }
}
