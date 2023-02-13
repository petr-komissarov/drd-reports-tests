package com.unlimint.drd.configs;


import com.unlimint.drd.helpers.EnvironmentVars;
import lombok.Synchronized;

import static com.codeborne.selenide.Configuration.*;

public class SelenideSettings {
    private static final String WIDTH = "1366";
    private static final String HEIGHT = "768";
    private static final EnvironmentVars ENVIRONMENT_VARS = new EnvironmentVars();
    private static boolean alreadyExecuted = false;

    private SelenideSettings() {
        throw new IllegalStateException("Utility class");
    }

    @Synchronized
    public static void setup() {
        if (alreadyExecuted) {
            return;
        }

        baseUrl = ENVIRONMENT_VARS.getBaseUrl();
        browser = ENVIRONMENT_VARS.getBrowserName();
        browserSize = WIDTH + "x" + HEIGHT;
        savePageSource = false;
        timeout = 35 * 1000;
        pageLoadTimeout = 35 * 1000;
        pollingInterval = (long) (0.05 * 1000);
        setRunModeSpecificSettings();

        alreadyExecuted = true;
    }

    private static void setRunModeSpecificSettings() {
        if (ENVIRONMENT_VARS.isRunModeDebug()) {
            holdBrowserOpen = true;
        } else {
            driverManagerEnabled = false;
            remote = ENVIRONMENT_VARS.getRemoteUrl();
        }
    }
}
