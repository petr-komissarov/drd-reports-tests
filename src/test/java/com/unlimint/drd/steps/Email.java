package com.unlimint.drd.steps;

import com.unlimint.drd.data.dto.ImapsSettings;
import com.unlimint.drd.helpers.ProxySettings;
import com.unlimint.drd.helpers.TestPaths;
import io.qameta.allure.Step;
import lombok.Synchronized;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SubjectTerm;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Properties;

import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;
import static java.util.Date.from;
import static javax.mail.Flags.Flag.SEEN;
import static javax.mail.Folder.READ_WRITE;
import static javax.mail.Part.ATTACHMENT;
import static javax.mail.Session.getDefaultInstance;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.awaitility.Awaitility.await;

public class Email {
    private final Logger logger = getLogger(Email.class);
    private final String address;
    private final String password;
    private final String subject;
    private Store store;
    private Folder inbox;
    private Message receivedEmail;

    public Email(String address, String password, String subject) {
        this.address = address;
        this.password = password;
        this.subject = subject;
        this.inbox = null;
        this.store = null;
    }

    @Step("Wait and get email")
    @Synchronized
    public Email waitAndGetEmail(ZonedDateTime sentDate) {
        final Message[] result = new Message[1];

        await().atMost(ofMinutes(5)).pollInterval(ofSeconds(1L)).until(() -> {
            logger.info("Checking the mailbox...");
            result[0] = getLatestUnreadEmailBySubject(subject, from(sentDate.toInstant()));

            return result[0] != null;
        });

        receivedEmail = result[0];
        return this;
    }

    @Step("Save email attachments")
    public void saveAttachments(String fileName) {
        if (receivedEmail != null) {
            try {
                if (receivedEmail.getContentType().contains("multipart")) {
                    saveAttachmentsToFile(fileName);
                }
            } catch (MessagingException e) {
                logger.error(e.getMessage());
            }
        }

        closeConnection();
    }

    private void saveAttachmentsToFile(String fileName) {
        try {
            Multipart multiPart = (Multipart) receivedEmail.getContent();

            for (int counter = 0; counter < multiPart.getCount(); counter++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(counter);

                if (ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    part.saveFile(new TestPaths().getActualPdfFile(fileName));
                }
            }
        } catch (MessagingException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Message getLatestUnreadEmailBySubject(String subject, Date sentDate) {
        closeConnection();

        try {
            Session session = getDefaultInstance(new Properties());
            store = session.getStore("imaps");
            ImapsSettings imapsSettings = new ProxySettings().getImapSettings();
            store.connect(imapsSettings.getHost(), imapsSettings.getPort(), address, password);

            inbox = store.getFolder("INBOX");
            inbox.open(READ_WRITE);

            AndTerm conditions = new AndTerm(
                    new SubjectTerm(".*" + subject + ".*"),
                    new FlagTerm(new Flags(SEEN), false)
            );

            Message[] messages = inbox.search(conditions);
            return getMessage(messages, sentDate);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    private Message getMessage(Message[] messages, Date sentDate) {
        for (Message message : messages) {
            try {
                if (message.getSentDate().after(sentDate)) {
                    inbox.setFlags(new Message[]{message}, new Flags(SEEN), true);
                    return message;
                }
            } catch (MessagingException e) {
                logger.error(e.getMessage());
            }
        }

        return null;
    }

    private void closeConnection() {
        try {
            if (inbox != null && inbox.isOpen()) {
                inbox.close(false);
            }
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }

        try {
            if (store != null) {
                store.close();
            }
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }
}
