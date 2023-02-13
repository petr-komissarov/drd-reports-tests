package com.unlimint.drd.steps.ui;

import com.codeborne.selenide.ElementsCollection;
import com.unlimint.drd.data.Locators;
import com.unlimint.drd.helpers.LocalDates;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.pageLoadTimeout;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class DatePicker {
    private static final String STRING_DATE_FORMAT = "MMMM, yyyy";
    private final By byCalendarLocator;

    public DatePicker(By byCalendarLocator) {
        this.byCalendarLocator = byCalendarLocator;
    }

    public void setDate(LocalDate newDate) {
        openDatepicker();

        final LocalDate[] currentMonthAndYear = {new LocalDates().getFromElementText(
                Locators.getCURRENT_MONTH_AND_YEAR_LBL(),
                STRING_DATE_FORMAT)
        };

        if (newDate.getYear() != currentMonthAndYear[0].getYear() || newDate.getMonth() != currentMonthAndYear[0].getMonth()) {
            new WebDriverWait(getAndCheckWebDriver(), pageLoadTimeout / 1000).until(driver -> {
                if (driver == null) {
                    return false;
                }

                $$(Locators.getPREVIOUS_MONTH_BTN())
                        .filter(visible)
                        .first()
                        .as("Go to the previous month button")
                        .click();

                currentMonthAndYear[0] = new LocalDates().getFromElementText(
                        Locators.getCURRENT_MONTH_AND_YEAR_LBL(),
                        STRING_DATE_FORMAT);

                return newDate.getYear() == currentMonthAndYear[0].getYear() && newDate.getMonth() == currentMonthAndYear[0].getMonth();
            });
        }

        DayOfWeek dayOfWeek = newDate.getDayOfWeek();

        ElementsCollection dayButtonCollection = SATURDAY == dayOfWeek || SUNDAY == dayOfWeek
                ? $$(Locators.getWEEKEND_DAY_BTN())
                : $$(Locators.getWEEK_DAY_BTN());

        dayButtonCollection
                .filter(matchText(".*" + newDate.getDayOfMonth() + ".*"))
                .filter(enabled)
                .last()
                .as("Calendar day with text " + newDate.getDayOfMonth() + " button")
                .click();
    }

    private void openDatepicker() {
        $(byCalendarLocator).as("Calendar button").click();
    }
}
