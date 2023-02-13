package com.unlimint.drd.helpers;

import org.apache.logging.log4j.Logger;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Paths.get;
import static org.apache.logging.log4j.LogManager.getLogger;

public class TestPaths {
    private static final String GIT_KEEP = ".gitkeep";
    private final CheckUtils checkUtils = new CheckUtils();
    private final EnvironmentVars environmentVars = new EnvironmentVars();
    private final Logger logger = getLogger(TestPaths.class);

    public File getAllureEnvironmentFile() {
        String allureResultsDirProperty = environmentVars.getAllureResultsDirectory();

        File allureResultsDir = checkUtils.isStringEmpty(allureResultsDirProperty)
                ? createDirIfNotExists(get(environmentVars.getUserDirectory(), "allure-results"))
                : createDirIfNotExists(get(allureResultsDirProperty));

        return new File(allureResultsDir, "environment.properties");
    }

    public File getUserYamlFile() {
        return verifyFileExists(new File(createDirIfNotExists(get(getResourcesDir(), "users")), environmentVars.getStandName() + ".yaml"));
    }

    public Path getLog4jConfigFile() {
        return verifyFileExists(new File(getResourcesDir(), "log4j2.xml")).toPath();
    }

    public File getActualPdfFile(String fileName) {
        File actualPdfDir = createDirIfNotExists(get(getPdfDir(), "actual"));
        createFileIfNotExists(new File(actualPdfDir, GIT_KEEP));

        return new File(actualPdfDir, fileName);
    }

    public File getExpectedPdfFile(String fileName) {
        File expectedPdfDir = createDirIfNotExists(get(getPdfDir(), "expected"));
        createFileIfNotExists(new File(expectedPdfDir, GIT_KEEP));

        return new File(expectedPdfDir, fileName);
    }

    public File getDiffPdfDir() {
        File expectedPdfDir = createDirIfNotExists(get(getPdfDir(), "diff"));
        createFileIfNotExists(new File(expectedPdfDir, GIT_KEEP));

        return expectedPdfDir;
    }

    public File[] getDiffScreenshotFiles(String fileName) {
        FilenameFilter filter = (file, name) -> name.contains(fileName.substring(0, fileName.lastIndexOf('.')));
        File diffPdfDir = getDiffPdfDir();
        String[] diffFilesNames = diffPdfDir.list(filter);

        if (diffFilesNames != null) {
            File[] result = new File[diffFilesNames.length];

            for (int counter = 0; counter < diffFilesNames.length; counter++) {
                result[counter] = new File(diffPdfDir, diffFilesNames[counter]);
            }

            return result;
        }

        return new File[]{};
    }

    private String getPdfDir() {
        return createDirIfNotExists(get(get(getResourcesDir(), "pdf", environmentVars.getStandName()).toString())).getAbsolutePath();
    }

    private String getResourcesDir() {
        return createDirIfNotExists(get("src", "test", "resources")).getAbsolutePath();
    }

    private File createDirIfNotExists(Path path) {
        try {
            createDirectories(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return path.toFile();
    }

    private void createFileIfNotExists(File file) {
        if (checkUtils.isFileExists(file)) {
            return;
        }

        try (OutputStream outputStream = newOutputStream(file.toPath())) {
            outputStream.write(new byte[]{});
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private File verifyFileExists(File file) {
        if (checkUtils.isFileExists(file)) {
            return file;
        }

        throw new AssertionFailedError("File " + file.getAbsolutePath() + " not found");
    }
}
