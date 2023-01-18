package com.epam.ld.module2.testing;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

public class TestWatcherExtension implements TestWatcher, AfterAllCallback {
    private static final StringBuilder builder = new StringBuilder();
    private static final String DELIMITER = " > ";

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        if (context != null && context.getTestClass().isPresent()) {
            builder.append(context.getTestClass().get().getSimpleName()).append(DELIMITER).append(context.getDisplayName());
        }
        builder.append(TestResultStatus.DISABLED).append(System.lineSeparator());
        reason.ifPresent(s -> builder.append(DELIMITER).append("  reason: ").append(s).append(System.lineSeparator()));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (context != null && context.getTestClass().isPresent()) {
            builder.append(context.getTestClass().get().getSimpleName()).append(DELIMITER).append(context.getDisplayName());
        }
        builder.append(DELIMITER).append(TestResultStatus.SUCCESSFUL).append(System.lineSeparator());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (context != null && context.getTestClass().isPresent()) {
            builder.append(context.getTestClass().get().getSimpleName()).append(DELIMITER).append(context.getDisplayName());
        }
        builder.append(DELIMITER).append(TestResultStatus.ABORTED).append(System.lineSeparator());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (context != null && context.getTestClass().isPresent()) {
            builder.append(context.getTestClass().get().getSimpleName()).append(DELIMITER).append(context.getDisplayName());
        }
        if (cause != null) {
            builder.append(' ').append(cause);
        }
        builder.append(DELIMITER).append(TestResultStatus.FAILED).append(System.lineSeparator());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        try (PrintWriter out = new PrintWriter("test_report.txt", "ISO-8859-1")) {
            out.println(LocalDateTime.now());
            out.println(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
