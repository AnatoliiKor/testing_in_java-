package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.util.Map;
import java.util.Scanner;

/**
 * The type Messenger.
 */
public class Messenger {
    private MailServer mailServer;
    private TemplateEngine templateEngine;
    private Utils utils;

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
        Map<String, String> tags = messenger.getTagMap(args, template);
        Client client = new Client();
        client.setAddresses(messenger.getUtils().getAddressesStringFromMap(messenger.getUtils().getMapFromJsonFile("client.json")));
        messenger.sendMessage(client, template, tags);



    }

    public Map<String, String> getTagMap(String[] args, Template template) {
        Map<String, String> tags;

        if (args.length == 2) {
            String inputJsonTagFile = args[0];
            tags = utils.getMapFromJsonFile(inputJsonTagFile);
            tags.put("output", args[1]);
        } else {
            tags = templateEngine.getTagsInConsoleMode(new Scanner(System.in), template);
            tags.put("output", "toConsole");
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
        mailServer.send(client.getAddresses(), messageContent);
    }

    public Utils getUtils() {
        return utils;
    }
}