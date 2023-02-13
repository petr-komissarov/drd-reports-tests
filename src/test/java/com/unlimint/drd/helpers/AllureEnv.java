package com.unlimint.drd.helpers;


import com.google.common.collect.ImmutableSortedMap;
import lombok.Synchronized;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.System.getProperties;
import static java.lang.System.getenv;
import static java.nio.file.Files.newOutputStream;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.logging.log4j.LogManager.getLogger;

public class AllureEnv {
    private static boolean alreadyExecuted = false;

    private AllureEnv() {
        throw new IllegalStateException("Utility class");
    }

    @Synchronized
    public static void setUp() {
        if (alreadyExecuted) {
            return;
        }

        setEnvironment();
        alreadyExecuted = true;
    }

    private static void setEnvironment() {
        String[] keys = new String[]{"name", "home", "version"};

        ImmutableSortedMap<String, String> props = ImmutableSortedMap.<String, String>naturalOrder()
                .putAll(filterMap(getProperties(), keys))
                .putAll(filterMap(getenv(), keys))
                .build();

        saveToFile(props, new TestPaths().getAllureEnvironmentFile());
    }

    private static Map<String, String> filterMap(Map<?, ?> map, String[] keys) {
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            for (String key : keys) {
                if (containsIgnoreCase(entry.getKey().toString(), key)) {
                    result.put(entry.getKey().toString().toLowerCase(), entry.getValue().toString().toLowerCase());
                }
            }
        }

        return result;
    }

    private static void saveToFile(Map<String, String> props, File path) {
        Properties properties = new Properties();
        properties.putAll(props);

        try (OutputStream outputStream = newOutputStream(path.toPath())) {
            properties.store(outputStream, "");
        } catch (Exception e) {
            getLogger(AllureEnv.class).error(e.getMessage());
        }
    }
}
