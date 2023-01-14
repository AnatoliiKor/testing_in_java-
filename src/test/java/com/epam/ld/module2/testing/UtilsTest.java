package com.epam.ld.module2.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class UtilsTest {

    @Test
    public void readTXTFileByFileNameFromResourceFolder() {
        String fileName = "templateTest.txt";
        String text = Utils.readFileAsString(fileName);
        String contain = "Thank";
        assertTrue(text.contains(contain));
    }

}