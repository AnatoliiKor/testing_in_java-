package com.epam.ld.module2.testing.template;

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

        for (Map.Entry<String, String> s : tags.entrySet()) {
            result = result.replaceAll("#\\{" + s.getKey() + "}", tags.get(s.getKey()));
        }
        return result;
    }

    /**
     * Search tags in template.
     *
     * @param templateBody the text from template
     * @return the set of tags as strings
     */
    Set<String> findTagsFromTemplateBody(String templateBody) {
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
     * @param template the Template instance
     * @return the map of tags
     */
    public Map<String, String> getTagsInConsoleMode(Scanner sc, Template template) {
        Map<String, String> tags = new HashMap<>();
        Set<String> rawTags = findTagsFromTemplateBody(template.getTemplateBody());
        rawTags.forEach(rawTag -> {
            System.out.println("Enter " + rawTag);
            String answer = sc.nextLine();
            if (answer.isEmpty()) {
                System.out.println("It can not be empty. Please enter again " + rawTag);
                answer = sc.nextLine();
                if (answer.isEmpty()) {
                    throw new MissingTagException("Tag can not be empty");
                }
            }
            tags.put(rawTag, answer);
        });
        return tags;
    }

    /**
     * Check that all required tags are provided or throw MissingTagException.
     *
     * @param templateBody the raw text of the template
     * @param providedTags the map with provided tags
     * @return true or MissingTagException
     */
    public boolean checkProvidedTagsForComplicity(String templateBody, Map<String, String> providedTags) {
        Set<String> requiredTags = findTagsFromTemplateBody(templateBody);
        requiredTags.forEach(tag -> {
            if (providedTags.get(tag) == null) {
                throw new MissingTagException("tag is missing: " + tag);
            }
        });
        return true;
    }
}
