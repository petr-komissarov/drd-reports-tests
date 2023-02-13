package com.unlimint.drd.helpers;

import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;

import static java.time.Duration.ofSeconds;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.awaitility.Awaitility.await;

public class TestSleep {
    private final Logger logger = getLogger(TestSleep.class);

    @Step("Sleep")
    public void waitInSec(long timeoutInSec) {
        logger.info("Wait for {} seconds", timeoutInSec);

        await()
                .timeout(ofSeconds(timeoutInSec * 2L))
                .pollDelay(ofSeconds(timeoutInSec))
                .until(() -> Boolean.TRUE);
    }
}
