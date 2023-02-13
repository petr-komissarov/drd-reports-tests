package com.unlimint.drd.helpers;

import com.unlimint.drd.data.dto.User;
import lombok.Synchronized;
import org.apache.logging.log4j.Logger;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.apache.logging.log4j.LogManager.getLogger;

public class UserIO {
    private static final File file = new TestPaths().getUserYamlFile();
    private final Logger logger = getLogger(UserIO.class);

    @Synchronized
    public User getUser(String login) {
        if (!new CheckUtils().isFileExists(file)) {
            throw new AssertionFailedError("File '" + file.getAbsolutePath() + "' not found");
        }

        try {
            User[] users = new JacksonMapper()
                    .getYamlMapper()
                    .readValue(file, User[].class);

            for (User user : users) {
                if (Objects.equals(user.getLogin(), login)) {
                    logger.info("User credentials: {} / {}", user.getLogin(), user.getPassword());
                    return user;
                }
            }

            throw new AssertionFailedError("User with login '{}' not found in the file {}", login, file.getAbsolutePath());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        throw new AssertionFailedError("Error occurred while reading from the file " + file.getAbsolutePath());
    }
}
