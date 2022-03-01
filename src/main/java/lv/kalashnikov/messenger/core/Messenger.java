package lv.kalashnikov.messenger.core;

import org.springframework.jms.core.JmsTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Messenger {

    private final String userName;
    private final JmsTemplate jms;

    public Messenger(String userName, JmsTemplate jms) {
        this.userName = userName;
        this.jms = jms;
    }

    public String getUserName() {
        return userName;
    }

    public void sendMessage(String text) {
        jms.send(session -> session.createTextMessage(
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
                        + "<" + userName + ">" + text));
    }

    public String receiveMessage() {
        return (String) jms.receiveAndConvert();
    }

}