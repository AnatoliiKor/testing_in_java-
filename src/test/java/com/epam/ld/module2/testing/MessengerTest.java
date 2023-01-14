package com.epam.ld.module2.testing;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessengerTest {


    @Test
    public void getTagsFromJsonFileAsMap() {
        String fileName = "input.json";
        Map<String, String> map = Messenger.getTagsFromJsonFile(fileName);
        assertEquals("Ivan", map.get("receiverName"));
    }

    @Test
    public void creationTagMapByConsole() {
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("answer");
        Map<String, String> map = Messenger.consoleMode(scanner);
        assertEquals("answer", map.get("receiverName"));
    }

}