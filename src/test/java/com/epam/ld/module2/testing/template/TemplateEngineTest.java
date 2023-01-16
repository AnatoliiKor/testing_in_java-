package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.exception.MissingTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class TemplateEngineTest {
    private Template template;
    private TemplateEngine templateEngine;

    @BeforeEach
    void startUp() {
        template = mock(Template.class);
        when(template.getTemplateBody()).thenReturn("text #{receiverName} text #{client}#{receiverName} text ");
        templateEngine = new TemplateEngine();
    }

    @Test
    public void generateMessage() {
        Map<String, String> tags = new HashMap<>();
        tags.put("receiverName", "Ivan");
        String templateWithoutTags = templateEngine.generateMessage(template, tags);
        assertTrue(templateWithoutTags.contains("Ivan"));
        assertFalse(templateWithoutTags.contains("receiverName"));
    }

    @Test
    public void checkThatAllRequiredTagsAreProvidedToGenerateMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("receiverName", "receiverName");
        map.put("client", "client");
        map.put("company", "company");
        String templateBody = "text #{receiverName} text #{client}#{receiverName} text#{company}";
        assertTrue(templateEngine.checkProvidedTagsForComplicity(templateBody, map));
    }

    @Test
    public void throwMissingTagException() {
        Map<String, String> map = new HashMap<>();
        map.put("receiverName", "receiverName");
        map.put("client", "client");
        String templateBody = "text #{receiverName} text #{client}#{receiverName} text#{company}";
        assertThrows(MissingTagException.class, ()-> templateEngine.checkProvidedTagsForComplicity(templateBody, map));
    }

    @Test
    public void findTags() {
        String templateBody = "text #{receiverName} text #{client}#{receiverName} text ";
        Set<String> tags = templateEngine.findTags(templateBody);
        assertEquals(2, tags.size());
    }

    @Test
    public void creationTagMapByConsole() {
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("answer");
        Map<String, String> map = templateEngine.getTagsInConsoleMode(scanner, template);
        assertEquals("answer", map.get("receiverName"));
    }

}
