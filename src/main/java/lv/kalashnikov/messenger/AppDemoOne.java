package lv.kalashnikov.messenger;

import lv.kalashnikov.messenger.core.Messenger;
import lv.kalashnikov.messenger.ui.MessengerUI;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class AppDemoOne {

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(AppDemoOne.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        JmsTemplate jms = context.getBean(JmsTemplate.class);
        jms.setPubSubDomain(true);

        Messenger messenger = new Messenger("Alexander", jms);
        new MessengerUI(messenger);

    }

}