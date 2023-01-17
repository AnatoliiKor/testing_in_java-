package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.exception.MissingTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class TemplateEngineTest {
    Template template;
    TemplateEngine templateEngine;
    final String TEMPLATE_BODY_WITH_THREE_TAGS = "text #{receiverName} text #{client}#{receiverName} text#{company}";
    Map<String, String> tags;

    @BeforeEach
    void startUp() {
        template = mock(Template.class);
        when(template.getTemplateBody()).thenReturn(TEMPLATE_BODY_WITH_THREE_TAGS);
        templateEngine = new TemplateEngine();
        tags = new HashMap<>();
    }

    @Test
    public void generateMessageAndReplacesTagsWithValues() {
        tags.put("receiverName", "value");
        String templateWithoutTags = templateEngine.generateMessage(template, tags);
        assertFalse(templateWithoutTags.contains("receiverName"));
        assertTrue(templateWithoutTags.contains("value"));
    }

    @Test
    public void checkThatAllRequiredTagsAreProvidedToGenerateMessage() {
        tags.put("receiverName", "receiverName");
        tags.put("client", "client");
        tags.put("company", "company");
        assertTrue(templateEngine.checkProvidedTagsForComplicity(TEMPLATE_BODY_WITH_THREE_TAGS, tags));
    }

    @Test
    public void shouldThrowMissingTagExceptionWhenNotAllTagsAreProvided() {
        tags.put("receiverName", "receiverName");
        tags.put("client", "client");
        assertThrows(MissingTagException.class, () ->
                templateEngine.checkProvidedTagsForComplicity(TEMPLATE_BODY_WITH_THREE_TAGS, tags));
    }

    @Test
    public void findTagsFromTemplateBodyAsSet() {
        Set<String> tags = templateEngine.findTagsFromTemplateBody(TEMPLATE_BODY_WITH_THREE_TAGS);
        assertEquals(3, tags.size());
    }

    @Test
    public void creationTagMapByConsoleWithProvidedAnswers() {
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("answer");
        tags = templateEngine.getTagsInConsoleMode(scanner, template);
        int numberOfTagsInTemplateBody = 3;
        assertEquals("answer", tags.get("receiverName"));
        verify(scanner, times(numberOfTagsInTemplateBody)).nextLine();
    }

    @Test
    public void throwsMissingTagExceptionInConsoleWhenEmptyAnswers() {
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("");
        assertThrows(MissingTagException.class, () ->
                templateEngine.getTagsInConsoleMode(scanner, template));
    }

    @Test
    public void verifySecondRequestToEnterTagWhenEmptyAnswersBeforeMissingTagExceptionInConsole() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("");
        assertThrows(MissingTagException.class, () ->
                templateEngine.getTagsInConsoleMode(scanner, template));
        verify(scanner, times(2)).nextLine();
        System.setOut(originalOut);
        assertTrue(outContent.toString().contains("It can not be empty. Please enter again"));
    }
}
