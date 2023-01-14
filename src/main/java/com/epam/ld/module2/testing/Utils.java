package com.epam.ld.module2.testing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {


    public static String readFileAsString(String fileName) {
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
}
