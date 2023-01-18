package com.epam.ld.module2.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {

    /**
     * Read file and returns it content as string.
     *
     * @param fileName the name of file
     * @return the String
     */
    public String readFileAsString(String fileName) {
        try {
            Path path = Paths.get(Objects.requireNonNull(Messenger.class.getClassLoader().getResource(fileName)).toURI());
            return String.join("\n", Files.readAllLines(path));
        } catch (Exception e) {System.err.println(e.getMessage());}
        return "";
    }

    /**
     * Get the map with tags as strings from json file.
     *
     * @param jsonFile the name of json file
     * @return the Map of tags
     */
    public Map<String, String> getMapFromJsonFile(String jsonFile) {
        Map<String, String> result;
        String tagString = readFileAsString(jsonFile);
        try {
            result = new ObjectMapper().readValue(tagString, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Get the String with addresses separated by comma from map of strings.
     *
     * @param addressesMap the map of strings with addresses
     * @return the String
     */
    public String getAddressesStringFromMapDelimitedByComma(Map<String, String> addressesMap) {
        return String.join(",", addressesMap.values());
    }

    /**
     * Write content to file.
     * @param fileName the absolute or relative path to file.
     * @param messageContent the content for writing.
     */
    public void writeToFile(String messageContent, File fileName) {
        try (PrintWriter out = new PrintWriter(fileName, "ISO-8859-1")) {
           out.println(messageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
