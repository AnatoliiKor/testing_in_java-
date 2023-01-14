package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The type Messenger.
 */
public class Messenger {
    private MailServer mailServer;
    private TemplateEngine templateEngine;

    /**
     * Instantiates a new Messenger.
     *
     * @param mailServer     the mail server
     * @param templateEngine the template engine
     */
    public Messenger(MailServer mailServer,
                     TemplateEngine templateEngine) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }


    public static void main(String[] args) {
        TemplateEngine templateEngine = new TemplateEngine();
        Map<String, String> tags;
        if (args.length == 2) {
            String inputJsonTagFile = args[0];
            tags = getTagsFromJsonFile(inputJsonTagFile);
            tags.put("outputFile", args[1]);
        } else {
            tags = consoleMode(new Scanner(System.in));
        }

    }

    public static Map<String, String> consoleMode(Scanner sc) {
        Map<String, String> tags = new HashMap<>();
        System.out.println("Enter receiver name");
        String ctr = sc.nextLine();
        tags.put("receiverName", sc.nextLine());
        System.out.println("Enter receiver email");
        tags.put("receiverEmailAddress", sc.nextLine());
        System.out.println("Enter company name");
        tags.put("companyName", sc.nextLine());
        return tags;
    }

    public static Map<String, String> getTagsFromJsonFile(String jsonFile) {
        Map<String, String> result;
        String tagString = Utils.readFileAsString(jsonFile);
        try {
            result = new ObjectMapper().readValue(tagString, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * Send message.
     *
     * @param client   the client
     * @param template the template
     */
    public void sendMessage(Client client, Template template) {
        String messageContent =
                templateEngine.generateMessage(template, client);
        mailServer.send(client.getAddresses(), messageContent);
    }

}