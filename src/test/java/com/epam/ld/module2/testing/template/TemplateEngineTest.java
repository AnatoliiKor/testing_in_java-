package com.epam.ld.module2.testing.template;

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
    private TemplateEngine engine;

    @BeforeEach
    void startUp() {
        template = mock(Template.class);
        when(template.getTemplateBody()).thenReturn("text #{receiverName} text #{client}#{receiverName} text ");
        engine = new TemplateEngine();
    }

    @Test
    public void generateMessage() {
        Map<String, String> tags = new HashMap<>();
        tags.put("receiverName", "Ivan");
        String templateWithoutTags = engine.generateMessage(template, tags);
        assertTrue(templateWithoutTags.contains("Ivan"));
        assertFalse(templateWithoutTags.contains("receiverName"));

    }

    @Test
    public void findTags() {
        String templateBody = "text #{receiverName} text #{client}#{receiverName} text ";
        Set<String> tags = engine.findTags(templateBody);
        assertEquals(2, tags.size());
    }

    @Test
    public void creationTagMapByConsole() {
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("answer");
        Map<String, String> map = engine.getTagsInConsoleMode(scanner, template);
        assertEquals("answer", map.get("receiverName"));
    }

}
