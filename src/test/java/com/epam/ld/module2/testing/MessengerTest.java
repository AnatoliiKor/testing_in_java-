package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class MessengerTest {
    Messenger messenger;
    TemplateEngine templateEngine;
    MailServer mailServer;
    Utils utils;
    Map<String, String> tags;
    Template template;

    @BeforeEach
    void startUp() {
        mailServer = mock(MailServer.class);
        utils = mock(Utils.class);
        templateEngine = mock(TemplateEngine.class);
        template = mock(Template.class);
        messenger = new Messenger(mailServer, templateEngine, utils);
    }

    @Test
    void getTagMapByPassingTwoArgumentsWithInputAndOutputFilesSelectFileMode() {
        String[] args = {"inputFile", "outputFile"};
        when(utils.getMapFromJsonFile(args[0])).thenReturn(new HashMap<>());
        tags = messenger.getTagMapInTwoMods(args, template);
        assertEquals("toFile", tags.get("outputMode"));
    }

    @Test
    void getTagMapByPassingZeroArgumentsSelectConsoleMode() {
        String[] args = {};
        when(templateEngine.getTagsInConsoleMode(any(Scanner.class), any(Template.class))).thenReturn(new HashMap<>());
        tags = messenger.getTagMapInTwoMods(args, template);
        assertEquals("toConsole", tags.get("outputMode"));
    }

    @Test
    void sendMessage() {
        when(templateEngine.generateMessage(any(Template.class), anyMap())).thenReturn("");
        Client client = mock(Client.class);
        when(client.getAddresses()).thenReturn("");
        tags = mock(Map.class);
        when(tags.get(anyString())).thenReturn("");
        messenger.sendMessage(client, template,tags);
        verify(mailServer).send(anyString(), anyString(), anyString());
    }
}