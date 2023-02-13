package com.unlimint.drd.helpers;

import lombok.Synchronized;
import org.apache.logging.log4j.core.config.ConfigurationSource;

import java.io.IOException;
import java.io.InputStream;

import static java.nio.file.Files.newInputStream;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.apache.logging.log4j.core.config.Configurator.initialize;

public class TestLogger {
    private static boolean alreadyExecuted = false;

    private TestLogger() {
        throw new IllegalStateException("Utility class");
    }

    @Synchronized
    public static void setup() {
        if (alreadyExecuted) {
            return;
        }

        try (InputStream inputStream = newInputStream(new TestPaths().getLog4jConfigFile())) {
            ConfigurationSource source = new ConfigurationSource(inputStream);
            initialize(null, source);
        } catch (IOException e) {
            getLogger(TestLogger.class).warn(e.getMessage());
        } finally {
            alreadyExecuted = true;
        }
    }
}
