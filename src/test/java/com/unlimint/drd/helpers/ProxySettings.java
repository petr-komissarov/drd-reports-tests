package com.unlimint.drd.helpers;

import com.unlimint.drd.data.dto.ImapsSettings;
import io.restassured.specification.RequestSpecification;

import static java.lang.Integer.parseInt;

public class ProxySettings {
    private final EnvironmentVars environmentVars = new EnvironmentVars();

    public RequestSpecification addProxyToClient(RequestSpecification client) {
        String hostProxy = environmentVars.getHttpsProxyHost();
        String portProxy = environmentVars.getHttpsProxyPort();

        if (hostProxy != null && portProxy != null) {
            return client.proxy(hostProxy, parseInt(portProxy));
        }

        return client;
    }

    public ImapsSettings getImapSettings() {
        ImapsSettings imapsSettings = new ImapsSettings();
        imapsSettings.setHost(environmentVars.getImapsProxyHost());
        imapsSettings.setPort(environmentVars.getImapsProxyPort());

        return imapsSettings;
    }
}
