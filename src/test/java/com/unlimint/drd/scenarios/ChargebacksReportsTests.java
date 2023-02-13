package com.unlimint.drd.scenarios;

import com.unlimint.drd.base.UiTest;
import com.unlimint.drd.data.dto.SaapData;
import com.unlimint.drd.data.dto.User;
import com.unlimint.drd.helpers.LocalDates;
import com.unlimint.drd.helpers.PdfValidator;
import com.unlimint.drd.helpers.UserIO;
import com.unlimint.drd.steps.Api;
import com.unlimint.drd.steps.Email;
import com.unlimint.drd.steps.ui.ChargebacksReport;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static java.time.ZonedDateTime.now;
import static java.util.Calendar.JANUARY;
import static org.junit.jupiter.params.provider.Arguments.of;

@DisplayName("DRD Reports")
@Feature("Chargebacks Reports")
@Tag("Chargebacks")
@Severity(CRITICAL)
public class ChargebacksReportsTests extends UiTest {
    private static final String CHB_REPORT_EMAIL_SUBJECT = "Disputes Resolution Center: Chargebacks report";
    private static final User REPORT_USER = new UserIO().getUser("saap_drd");
    private static final String VAULT_SECRET_TOKEN = REPORT_USER.getVaultSecretToken();
    private static final User DEFAULT_USER = REPORT_USER.createCopy();
    private static final Object LOCKER = new Object();

    @BeforeAll
    public static void beforeAll() {
        Api api = new Api();
        SaapData saapData = api.getSaapVaultSecret(VAULT_SECRET_TOKEN);

        DEFAULT_USER.setDbDatabase(saapData.getDrcMongodbDatabase());
        DEFAULT_USER.setDbUser(saapData.getDrcMongodbUser());
        DEFAULT_USER.setDbPassword(saapData.getDrcMongodbPassword());

        api.switchDB(REPORT_USER, saapData);
    }

    @AfterAll
    public static void afterAll() {
        Api api = new Api();
        SaapData saapData = api.getSaapVaultSecret(VAULT_SECRET_TOKEN);
        api.switchDB(DEFAULT_USER, saapData);
    }

    private static Stream<Arguments> getPeriods() {
        LocalDate from = new LocalDates().createLocalDate(2021, JANUARY, 21);
        LocalDate to = new LocalDates().createLocalDate(2021, JANUARY, 27);

        return Stream.of(
                of(from, to),
                of(from.plusDays(1), to.plusDays(1)),
                of(from.plusDays(2), to.plusDays(2)),
                of(from.plusDays(3), to.plusDays(3)),
                of(from.plusDays(4), to.plusDays(4)));
    }

    @DisplayName("Create Chargebacks Report Positive")
    @ParameterizedTest(name = "Create Chargebacks Report Positive {0} - {1}")
    @MethodSource("getPeriods")
    public void createChargebacksReportPositive(LocalDate from, LocalDate to) {
        ChargebacksReport chargebacksReport = new ChargebacksReport()
                .doLogin(REPORT_USER.getLogin(), REPORT_USER.getPassword())
                .goToChbReports()
                .selectCheckBoxes(true,
                        true,
                        true,
                        true,
                        true,
                        true)
                .setResolvedChargebacksPeriod(from, to)
                .setFromActiveChargebacks(to);

        synchronized (LOCKER) {
            ZonedDateTime sentDate = now();

            chargebacksReport
                    .doCreateReport()
                    .waitReportIsSentToEmail()
                    .doLogout();

            new Email(REPORT_USER.getEmail(), REPORT_USER.getEmailPassword(), CHB_REPORT_EMAIL_SUBJECT)
                    .waitAndGetEmail(sentDate)
                    .saveAttachments(reportName);
        }

        new PdfValidator().assertPdfFiles(reportName);
    }
}
