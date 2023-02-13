package com.unlimint.drd.helpers;

import com.testautomationguru.utility.PDFUtil;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static com.testautomationguru.utility.CompareMode.VISUAL_MODE;
import static io.qameta.allure.Allure.addAttachment;
import static java.awt.Color.RED;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.logging.Level.ALL;
import static org.apache.logging.log4j.LogManager.getLogger;

public class PdfValidator {
    private final Logger logger = getLogger(PdfValidator.class);

    @Step("Assert PDF reports")
    public void assertPdfFiles(String fileName) {
        TestPaths testPaths = new TestPaths();
        File actualFile = testPaths.getActualPdfFile(fileName);
        File expectedFile = testPaths.getExpectedPdfFile(fileName);

        if (!new CheckUtils().isFileExists(expectedFile)) {
            try {
                copy(actualFile.toPath(), expectedFile.toPath(), REPLACE_EXISTING);
                return;
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        boolean isEqual = false;

        try {
            PDFUtil pdfUtil = new PDFUtil();
            pdfUtil.enableLog();
            pdfUtil.setLogLevel(ALL);
            pdfUtil.compareAllPages(true);
            pdfUtil.setCompareMode(VISUAL_MODE);
            pdfUtil.highlightPdfDifference(true);
            pdfUtil.highlightPdfDifference(RED);
            pdfUtil.setImageDestinationPath(testPaths.getDiffPdfDir().getAbsolutePath());

            if (pdfUtil.getPageCount(expectedFile.getAbsolutePath()) != pdfUtil.getPageCount(actualFile.getAbsolutePath())) {
                throw new AssertionFailedError("PDF files have different pages count", expectedFile, actualFile);
            }

            isEqual = pdfUtil.compare(expectedFile.getAbsolutePath(), actualFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        if (isEqual) {
            return;
        }

        attachFile("actual", actualFile);
        attachFile("expected", expectedFile);
        File[] diffScreenshotFiles = testPaths.getDiffScreenshotFiles(fileName);

        for (int counter = 0; counter < diffScreenshotFiles.length; counter++) {
            attachFile("diff. Page " + (counter + 1), diffScreenshotFiles[counter]);
        }

        throw new AssertionFailedError("PdfValidator files are not equal", expectedFile, actualFile);
    }

    private void attachFile(String attachmentName, File filePath) {
        try (ByteArrayInputStream result = new ByteArrayInputStream(readAllBytes(filePath.toPath()))) {
            addAttachment(attachmentName, result);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
