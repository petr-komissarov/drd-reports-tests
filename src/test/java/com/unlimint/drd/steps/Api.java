package com.unlimint.drd.steps;

import com.google.common.collect.ImmutableMap;
import com.unlimint.drd.data.URLs;
import com.unlimint.drd.data.dto.SaapData;
import com.unlimint.drd.data.dto.User;
import com.unlimint.drd.helpers.ProxySettings;
import com.unlimint.drd.helpers.TestSleep;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Logger;
import org.opentest4j.AssertionFailedError;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.apache.logging.log4j.LogManager.getLogger;


public class Api {
    private final Logger logger = getLogger(Api.class);

    @Step("Update saap vault secret")
    public void switchDB(User updatedUserData, SaapData currentData) {
        if (currentData.getDrcMongodbDatabase().equals(updatedUserData.getDbDatabase())) {
            logger.info("No need to update saap vault secret");
            return;
        }

        currentData.setDrcMongodbDatabase(updatedUserData.getDbDatabase());
        currentData.setDrcMongodbUser(updatedUserData.getDbUser());
        currentData.setDrcMongodbPassword(updatedUserData.getDbPassword());

        postSaapVaultSecret(currentData, updatedUserData.getVaultSecretToken());
        currentData = getSaapVaultSecret(updatedUserData.getVaultSecretToken());

        if (!currentData.getDrcMongodbDatabase().equals(updatedUserData.getDbDatabase())) {
            throw new AssertionFailedError(
                    "DRC_MONGODB_DATABASE not updated",
                    updatedUserData.getDbDatabase(),
                    currentData.getDrcMongodbDatabase()
            );
        }

        if (!currentData.getDrcMongodbUser().equals(updatedUserData.getDbUser())) {
            throw new AssertionFailedError(
                    "DRC_MONGODB_USER not updated",
                    updatedUserData.getDbUser(),
                    currentData.getDrcMongodbUser()
            );
        }

        if (!currentData.getDrcMongodbPassword().equals(updatedUserData.getDbPassword())) {
            throw new AssertionFailedError(
                    "DRC_MONGODB_PASSWORD not updated",
                    updatedUserData.getDbPassword(),
                    currentData.getDrcMongodbPassword()
            );
        }

        logger.info("Saap vault secret updated");
        restartCardpaySaapService(updatedUserData);
        new TestSleep().waitInSec(20);
    }

    @Step("Get saap vault secret")
    public SaapData getSaapVaultSecret(String vaultSecretToken) {
        return getClient()
                .with().header("x-vault-token", vaultSecretToken)
                .when().get(URLs.getVAULT_SECRET())
                .then().assertThat().statusCode(SC_OK)
                .and().extract().jsonPath()
                .and().getObject("data", SaapData.class);
    }

    @Step("Post saap vault secret")
    private void postSaapVaultSecret(Object pojo, String vaultSecretToken) {
        getClient()
                .with().header("x-vault-token", vaultSecretToken)
                .and().body(pojo)
                .when().post(URLs.getVAULT_SECRET())
                .then().assertThat().statusCode(SC_NO_CONTENT);
    }

    @Step("Restart cardpay-saap service")
    private void restartCardpaySaapService(User user) {
        ImmutableMap<String, String> params = ImmutableMap.<String, String>builder()
                .put("COMMAND", "saap_restart")
                .put("TARGET_HOST", "saap.cardpay-test.com")
                .put("PRODUCT", "cardpay-saap")
                .build();

        getClient()
                .with().auth().preemptive().basic(user.getJenkinsUser(), user.getJenkinsToken())
                .and().queryParams(params)
                .when().post(URLs.getJENKINS_CARDPAY_CONTROL_JOB())
                .then().assertThat().statusCode(SC_CREATED);
        logger.info("'cardpay-saap' service restarted on the host 'saap.cardpay-test.com'");
    }

    private RequestSpecification getClient() {
        return new ProxySettings()
                .addProxyToClient(given())
                .filter(new AllureRestAssured());
    }
}
