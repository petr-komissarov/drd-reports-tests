package com.unlimint.drd.base;

import com.unlimint.drd.configs.SelenideSettings;
import com.unlimint.drd.helpers.AllureEnv;
import com.unlimint.drd.helpers.EnvironmentVars;
import com.unlimint.drd.helpers.TestLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static com.codeborne.selenide.logevents.SelenideLogger.hasListener;

@Tag("All")
public abstract class UiTest {
    static {
        SelenideSettings.setup();
        TestLogger.setup();
        AllureEnv.setUp();
    }

    protected String reportName;

    @BeforeEach
    public void beforeEach(TestInfo info) {
        reportName = info.getDisplayName() + ".pdf";

        if (hasListener("AllureSelenide")) {
            return;
        }

        addListener("AllureSelenide", new AllureSelenide()
                .includeSelenideSteps(true)
                .screenshots(true)
                .savePageSource(false));
    }

    @AfterEach
    public void afterEach() {
        if (new EnvironmentVars().isRunModeDebug()) {
            return;
        }

        closeWebDriver();
    }
}
