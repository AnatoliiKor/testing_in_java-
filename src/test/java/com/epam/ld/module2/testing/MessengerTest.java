package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(TestWatcherExtension.class)
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
        assertEquals(Constants.TO_FILE, tags.get(Constants.OUTPUT_MODE));
    }

    @Test
    void getTagMapByPassingZeroArgumentsSelectConsoleMode() {
        String[] args = {};
        when(templateEngine.getTagsInConsoleMode(any(Scanner.class), any(Template.class))).thenReturn(new HashMap<>());
        tags = messenger.getTagMapInTwoMods(args, template);
        assertEquals(Constants.TO_CONSOLE, tags.get(Constants.OUTPUT_MODE));
    }

    @Test
    void sendMessage() {
        when(templateEngine.generateMessage(any(Template.class), anyMap())).thenReturn("message content");
        Client client = mock(Client.class);
        when(client.getAddresses()).thenReturn("addresses");
        tags = mock(Map.class);
        when(tags.get(anyString())).thenReturn("mode");
        messenger.sendMessage(client, template,tags);
        verify(mailServer).send("addresses", "message content", "mode");
    }
}