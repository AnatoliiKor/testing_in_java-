package com.epam.ld.module2.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {


    public String readFileAsString(String fileName) {
        try {
            Path path = Paths.get(Objects.requireNonNull(Messenger.class.getClassLoader().getResource(fileName)).toURI());
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            return data;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

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
    public String getAddressesStringFromMap(Map<String, String> addressesMap) {
        return String.join(",", addressesMap.values());
    }

}
