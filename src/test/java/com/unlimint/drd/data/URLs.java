package com.unlimint.drd.data;

import lombok.Getter;

public final class URLs {
    @Getter
    private static final String DRD = "/saap/drd/#/";

    @Getter
    private static final String VAULT_SECRET = "https://vault-cy.cardpay-test.com/v1/secret/cardpay/preprod/saap";

    @Getter
    private static final String JENKINS_CARDPAY_CONTROL_JOB = "https://jenkins.cardpay-test.com/job/cardpay-control/buildWithParameters";

    private URLs() {
        throw new IllegalStateException("Utility class");
    }
}
