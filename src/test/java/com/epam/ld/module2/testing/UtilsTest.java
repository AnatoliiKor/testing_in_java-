package com.epam.ld.module2.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    Utils utils;

    @BeforeEach
    void startUp() {
        utils = new Utils();
    }

    @Test
    public void readTXTFileByFileNameFromResourceFolder() {
        String fileName = "templateTest.txt";
        String text = utils.readFileAsString(fileName);
        String contain = "Thank";
        assertTrue(text.contains(contain));
    }

    @Test
    public void getMapFromJson() {
        String fileName = "input.json";
        Map<String, String> map = utils.getMapFromJsonFile(fileName);
        assertEquals("Ivan", map.get("receiverName"));
    }


    @Test
    public void throwExceptionWhenInputFileNotJsp() {
        assertThrows(RuntimeException.class, ()->{utils.getMapFromJsonFile("templateTest.txt");});
    }

    @Test
    void getAddressesStringFromMap() {
        String expectedString = "one,two,three";
        Map<String, String> map = new HashMap<>();
        map.put("one", "one");
        map.put("two", "two");
        map.put("three", "three");
        assertEquals(expectedString, utils.getAddressesStringFromMap(map));
    }
}