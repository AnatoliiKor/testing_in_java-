package com.epam.ld.module2.testing;

import java.io.File;

/**
 * Mail server class.
 */
public class MailServer {
    private File file;

    /**
     * Send notification.
     *
     * @param addresses      the addresses
     * @param messageContent the message content
     * @param outputMode     the target of the output is either "toConsole" or Constants.TO_FILE
     */
    public void send(String addresses, String messageContent, String outputMode) {
        if (Constants.TO_FILE.equals(outputMode)) {
            new Utils().writeToFile(messageContent, file);
            System.out.println("Message was saved to " + file);
        } else {
            System.out.println("Message: \n" + messageContent + "\nwas sent to addresses: " + addresses);
        }
    }

    public void setFile(File file) {
        this.file = file;
    }
}
