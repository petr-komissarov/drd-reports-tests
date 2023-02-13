package com.unlimint.drd.helpers;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static java.lang.management.ManagementFactory.getRuntimeMXBean;

public class EnvironmentVars {
    public String getStandName() {
        return resolveProperty("stand.name", "preprod")
                .toLowerCase()
                .replace("-", "");
    }

    public String getBaseUrl() {
        switch (getStandName()) {
            case ("test"):
                return "https://test.cardpay-test.com";
            case ("nsk"):
                return "https://waf-nsk.cardpay-test.com";
            case ("dev"):
                return "https://waf-dev.cardpay-test.com";
            default:
                return "https://preprod.cardpay-test.com";
        }
    }

    public String getRemoteUrl() {
        switch (getStandName()) {
            case ("test"):
                return "http://swarm1-test.cardpay-test.com:4444/wd/hub";
            case ("nsk"):
                return "http://swarm-test-nsk01.cardpay-test.com:4444/wd/hub";
            case ("dev"):
                return "http://docker2.cardpay-test.com:4444/wd/hub";
            case ("ma"):
                return "http://qa.cardpay-test.com:4444/wd/hub";
            default:
                return "http://swarm1-preprod.cardpay-test.com:4444/wd/hub";
        }
    }

    public String getHttpsProxyHost() {
        return resolveProperty("https.proxy.host", null);
    }

    public String getHttpsProxyPort() {
        return resolveProperty("https.proxy.port", null);
    }

    public String getImapsProxyHost() {
        return resolveProperty("imaps.proxy.host", "proxy-cy.cardpay-test.com");
    }

    public int getImapsProxyPort() {
        return parseInt(resolveProperty("imaps.proxy.port", "993"));
    }

    public String getBrowserName() {
        return resolveProperty("browser.name", "chrome");
    }

    public String getAllureResultsDirectory() {
        return getProperty("allure.results.directory");
    }

    public String getUserDirectory() {
        return getProperty("user.dir");
    }

    public boolean isRunModeDebug() {
        return getRuntimeMXBean()
                .getInputArguments()
                .toString()
                .contains("-agentlib:jdwp");
    }

    private String resolveProperty(String propertyName, String defaultValue) {
        String property = getProperty(propertyName);

        if (new CheckUtils().isStringEmpty(property)) {
            return defaultValue;
        }

        return property;
    }
}
