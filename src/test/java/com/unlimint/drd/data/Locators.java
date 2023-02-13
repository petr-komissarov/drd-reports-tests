package com.unlimint.drd.data;

import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;

public final class Locators {
    @Getter
    private static final By USER_NAME_TXB = byId("username");

    @Getter
    private static final By PASSWORD_TXB = byId("password");

    @Getter
    private static final By SIGN_IN_BTN = byName("login");

    @Getter
    private static final By USER_DROPDOWN = byXpath("//div[contains(@class,'user-bar__name ml-1')]");

    @Getter
    private static final By LOGOUT_BTN = byXpath("//button[contains(@class,'dropdown-item')]");

    @Getter
    private static final By CHB_REPORTS_BTN = byXpath("//button[@class='btn btn-download round-padding'][contains(.,'Chb Reports')]");

    @Getter
    private static final By RESOLVED_CHARGEBACKS_CBX = byXpath("//span[contains(.,'Resolved chargebacks')]");

    @Getter
    private static final By ACCEPTED_CBX = byXpath("//span[contains(.,'Accepted')]");

    @Getter
    private static final By WON_CBX = byXpath("//span[contains(.,'Won')]");

    @Getter
    private static final By LOST_CBX = byXpath("//span[contains(.,'Lost')]");

    @Getter
    private static final By ACTIVE_CHARGEBACKS_CBX = byXpath("//span[contains(.,'Active chargebacks')]");

    @Getter
    private static final By MERCHANTS_CBX = byXpath("//span[contains(.,'Merchants')]");

    @Getter
    private static final By FROM_RESOLVED_CHARGEBACKS_TXB = byXpath("(//span[contains(@class,'control-value__date')])[1]");

    @Getter
    private static final By TO_RESOLVED_CHARGEBACKS_TXB = byXpath("(//span[contains(@class,'control-value__date')])[2]");

    @Getter
    private static final By FROM_ACTIVE_CHARGEBACKS_TXB = byXpath("(//span[contains(@class,'control-value__date')])[3]");

    @Getter
    private static final By CREATE_REPORT_BTN = byXpath("//button[@class='btn btn-success'][contains(.,'Create')]");

    @Getter
    private static final By PLEASE_WAIT_WHILE_REPORT_IS_SENT_TO_YOUR_EMAIL_TXT = byXpath("//div[@class='rtn-notification__title ng-tns-c16-1 ng-star-inserted'][contains(.,'Please wait while report is sent to your email...')]");

    @Getter
    private static final By CURRENT_MONTH_AND_YEAR_LBL = byXpath("(//div[contains(@class,'month-select__value')])[3]");

    @Getter
    private static final By PREVIOUS_MONTH_BTN = byXpath("//app-icon-arrow[@class='month-select__prev']");

    @Getter
    private static final By WEEKEND_DAY_BTN = byXpath("//div[@class='calendar__day calendar__day--weekend calendar__day--active-month ng-star-inserted']");

    @Getter
    private static final By WEEK_DAY_BTN = byXpath("//div[@class='calendar__day calendar__day--active-month ng-star-inserted']");

    private Locators() {
        throw new IllegalStateException("Utility class");
    }
}
