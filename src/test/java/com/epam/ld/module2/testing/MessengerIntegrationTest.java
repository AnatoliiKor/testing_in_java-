package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Spy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(TestWatcherExtension.class)
public class MessengerIntegrationTest {
    @Spy
    TemplateEngine templateEngine;
    @Spy
    MailServer mailServer;
    @TempDir
    static Path tempDir;

    @Test
    @Tag("IntegrationTest")
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_13)
    public void testSendingMessageInConsoleMode() {
        String[] args = {};
        Map<String, String> tags = new HashMap<>();
        tags.put("receiverName", "receiverName");
        tags.put("companyName", "companyName");

        templateEngine = spy(new TemplateEngine());
        doReturn(tags).when(templateEngine).getTagsInConsoleMode(any(Scanner.class), any(Template.class));

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Messenger messenger = new Messenger(new MailServer(), templateEngine, new Utils());
        messenger.runService(args);

        String actual = outContent.toString();
        System.setOut(originalOut);
        assertTrue(actual.contains("Thank you again for your interest"));
    }

    @Test
    @Tag("IntegrationTest")
    @DisabledForJreRange(min = JRE.JAVA_14, max = JRE.JAVA_15)
    public void testSendingMessageInFileMode() throws IOException {
        String outputFile = "output_integration_test.txt";
        String[] args = {"input.json", outputFile};
        Path filePath = tempDir.resolve(outputFile);

        mailServer = spy(new MailServer());
        mailServer.setFile(filePath.toFile());
        doNothing().when(mailServer).setFile(any(File.class));

        Messenger messenger = new Messenger(mailServer, new TemplateEngine(), new Utils());
        messenger.runService(args);

        String actualTemplate = String.join("", Files.readAllLines(filePath));
        assertTrue(actualTemplate.contains("Thank you again for your interest in Global Systems"));
    }
}
