package com.unlimint.drd.helpers;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.$;
import static java.time.LocalDate.now;
import static java.time.ZoneId.systemDefault;
import static java.util.Locale.ENGLISH;
import static org.apache.logging.log4j.LogManager.getLogger;

public class LocalDates {
    private final Logger logger = getLogger(LocalDates.class);

    public LocalDate createLocalDate(int year, int month, int dayOfMonth) {
        return new GregorianCalendar(year, month, dayOfMonth)
                .toInstant()
                .atZone(systemDefault())
                .toLocalDate();
    }

    public LocalDate getFromElementText(By by, String dateFormat) {
        LocalDate currentFrom = now();

        try {
            currentFrom = new SimpleDateFormat(dateFormat, ENGLISH)
                    .parse($(by).text())
                    .toInstant()
                    .atZone(systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        return currentFrom;
    }
}
