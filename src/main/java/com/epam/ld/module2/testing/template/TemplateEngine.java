package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.MissingTagException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    /**
     * Generate message string.
     *
     * @param template the template
     * @param tags     the map of tag values
     * @return the string
     */
    public String generateMessage(Template template, Map<String, String> tags) {
        String result = template.getTemplateBody();

        for (String s : tags.keySet()) {
            result = result.replaceAll("#\\{" + s + "}", tags.get(s));
        }
        return result;
    }

    /**
     * Search tags in template.
     *
     * @param templateBody the text from template
     * @return the set of tags as strings
     */
    Set<String> findTags(String templateBody) {
        Set<String> tags = new TreeSet<>();
        Pattern pattern = Pattern.compile("(#\\{)(.+?)(})");
        Matcher matcher = pattern.matcher(templateBody);
        while (matcher.find()) {
            tags.add(matcher.group(2));
        }
        return tags;
    }

    /**
     * Request tags from user by console.
     *
     * @param sc the Scanner instance
     * @return the map of tags
     */
    public Map<String, String> getTagsInConsoleMode(Scanner sc, Template template) {
        Map<String, String> tags = new HashMap<>();
        Set<String> rawTags = findTags(template.getTemplateBody());
        rawTags.forEach(rawTag -> {
            System.out.println("Enter " + rawTag);
            tags.put(rawTag, sc.nextLine());
        });
        return tags;
    }

    public boolean checkProvidedTagsForComplicity(String templateBody, Map<String, String> providedTags) {
        Set<String> requiredTags = findTags(templateBody);
        requiredTags.forEach(tag -> {
            if (providedTags.get(tag) == null) throw new MissingTagException("tag is missing: " + tag);
        });
        return true;
    }
}
