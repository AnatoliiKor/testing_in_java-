package com.epam.ld.module2.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestWatcherExtension.class)
class UtilsTest {
    Utils utils;
    @TempDir
    static Path tempDir;

    @BeforeEach
    void startUp() {
        utils = new Utils();
    }

    @Test
    @Tag("UtilsTest")
    public void readTXTFileByFileNameFromResourceFolder() {
        String fileName = "templateTest.txt";
        String text = utils.readFileAsString(fileName);
        String contain = "Last row";
        assertTrue(text.contains(contain));
    }

    @Test
    @Tag("UtilsTest")
    public void getMapOfStringsFromJsonFileByProvidedFileName() {
        String fileName = "input.json";
        Map<String, String> map = utils.getMapFromJsonFile(fileName);
        assertEquals("Ivan", map.get("receiverName"));
    }

    @Test
    @Tag("UtilsTest")
    public void throwExceptionWhenInputFileNotJsp() {
        assertThrows(RuntimeException.class, ()-> utils.getMapFromJsonFile("templateTest.txt"));
    }

    @Test
    @Tag("UtilsTest")
    void getAddressesStringFromMapDelimitedByComma() {
        String expectedString = "one,two,three";
        Map<String, String> map = new HashMap<>();
        map.put("one", "one");
        map.put("two", "two");
        map.put("three", "three");
        assertEquals(expectedString, utils.getAddressesStringFromMapDelimitedByComma(map));
    }

    @Test
    @Tag("UtilsTest")
    void writeToFile() throws IOException {
        Path filePath = tempDir.resolve("temp.file");
        utils.writeToFile( "file content", filePath.toFile());
        String actualTemplate = String.join("", Files.readAllLines(filePath));
        assertTrue(actualTemplate.contains("file content"));
    }
}