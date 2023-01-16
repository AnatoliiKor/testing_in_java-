package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MessengerTest {
    Messenger messenger;
    TemplateEngine templateEngine;

    @BeforeEach
    void startUp() {

        messenger = new Messenger(new MailServer(), new TemplateEngine(), new Utils());
    }

    @Test
    void getTagMapForFileMode() {
        String[] args = {"inputFile", "outputFile"};
        Template template = mock(Template.class);
        Utils utils = mock(Utils.class);
        Map<String, String> tags = new HashMap<>();
        messenger = new Messenger(new MailServer(), templateEngine, utils);
        when(utils.getMapFromJsonFile(args[0])).thenReturn(tags);
        tags = messenger.getTagMap(args, template);
        assertEquals("outputFile", tags.get("output"));
    }

    @Test
    void getTagMapForConsoleMode() {
        String[] args = {};
        Template template = mock(Template.class);
        templateEngine = mock(TemplateEngine.class);
        Map<String, String> tags = new HashMap<>();
        when(templateEngine.getTagsInConsoleMode(any(Scanner.class), any(Template.class))).thenReturn(tags);
        messenger = new Messenger(new MailServer(), templateEngine, new Utils());
        tags = messenger.getTagMap(args, template);
        assertEquals("toConsole", tags.get("output"));
    }

    @Test
    void sendMessage() {
        templateEngine = mock(TemplateEngine.class);
        when(templateEngine.generateMessage(any(Template.class), anyMap())).thenReturn("");
        MailServer mailServer = mock(MailServer.class);
        messenger = new Messenger(mailServer, templateEngine, new Utils());
        Client client = mock(Client.class);
        when(client.getAddresses()).thenReturn("");
        messenger.sendMessage(client, mock(Template.class), mock(Map.class));
        verify(mailServer).send(anyString(), anyString());

    }

}