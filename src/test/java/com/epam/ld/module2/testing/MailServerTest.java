package com.epam.ld.module2.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MailServerTest {
    MailServer mailServer;

    @TempDir
    static Path tempDir;

    @BeforeEach
    public void setUp() {
        mailServer = new MailServer();
    }

    @Test
    void shouldPrintMessageToConsole() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        String messageContent = "message content from console";
        mailServer.send("name@gmail.com", messageContent, "toConsole");
        String actual = outContent.toString();
        System.setOut(originalOut);
        assertTrue(actual.contains(messageContent));
    }

    @Test
    void shouldSaveMessageToFile() throws Exception {
        Path filePath = tempDir.resolve("temp.file");
        mailServer.setFile(filePath.toFile());
        mailServer.send("name@mail.com", "message content from file", "toFile");
        String actualTemplate = Files.readAllLines(filePath).stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertTrue(actualTemplate.contains("message content from file"));
    }
}
