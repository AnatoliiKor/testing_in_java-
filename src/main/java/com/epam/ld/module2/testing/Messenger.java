package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

/**
 * The type Messenger.
 */
public class Messenger {
    private final MailServer mailServer;
    private final TemplateEngine templateEngine;
    private final Utils utils;

    /**
     * Instantiates a new Messenger.
     *
     * @param mailServer     the mail server
     * @param templateEngine the template engine
     */
    public Messenger(MailServer mailServer,
                     TemplateEngine templateEngine,
                     Utils utils) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
        this.utils = utils;
    }


    public static void main(String[] args) {
        Messenger messenger = new Messenger(new MailServer(), new TemplateEngine(), new Utils());
        Template template = new Template();
        template.setTemplateBody(messenger.getUtils().readFileAsString("templateBody.txt"));
        Client client = new Client();
        client.setAddresses(messenger.getUtils().getAddressesStringFromMap(messenger.getUtils().getMapFromJsonFile("client.json")));
        messenger.sendMessage(client, template, messenger.getTagMap(args, template));
    }

    public Map<String, String> getTagMap(String[] args, Template template) {
        Map<String, String> tags;

        if (args.length == 2) {
            String inputJsonTagFile = args[0];
            tags = utils.getMapFromJsonFile(inputJsonTagFile);
            tags.put("outputMode", "toFile");
            this.getMailServer().setFile(new File(args[1]));
        } else {
            tags = templateEngine.getTagsInConsoleMode(new Scanner(System.in), template);
            tags.put("outputMode", "toConsole");
        }
        return tags;
    }


    /**
     * Send message.
     *
     * @param client   the client
     * @param template the template
     * @param tags the map of tag values
     */
    public void sendMessage(Client client, Template template, Map<String, String> tags) {
        String messageContent = templateEngine.generateMessage(template, tags);
        mailServer.send(client.getAddresses(), messageContent, tags.get("outputMode"));
    }

    public Utils getUtils() {
        return utils;
    }

    public MailServer getMailServer() {
        return mailServer;
    }
}