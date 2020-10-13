package hu.gdf.balazsbole.email.imap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.support.DefaultMailHeaderMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Slf4j
//@Component
public class EmailReceiver {

    @Value("${spring.kafka.topic.emailIn}")
    private String topicName;


    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
                "/gmail-imap-idle-config.xml");

        ImapMailReceiver mailReceiver = new ImapMailReceiver("imaps://helpdesk.gdf:Tha0xi7k@imap.yandex.com:993/inbox");
        mailReceiver.setShouldMarkMessagesAsRead(false);
        mailReceiver.setShouldDeleteMessages(false);
        mailReceiver.setSimpleContent(false);
        mailReceiver.setHeaderMapper(new DefaultMailHeaderMapper());
        mailReceiver.setUserFlag("user-flag-1");
        ImapIdleChannelAdapter adapter = new ImapIdleChannelAdapter(mailReceiver);
        adapter.setAutoStartup(true);
        DirectChannel channel = new DirectChannel();
        adapter.setOutputChannel(channel);


        DirectChannel inputChannel = ac.getBean("receiveChannel", DirectChannel.class);
        inputChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                try {

                    MimeMessageParser parsed = new MimeMessageParser(((MimeMessage) message.getPayload())).parse();

                    parsed.getPlainContent();
                    parsed.getHtmlContent();

                    String text =  parsed.hasHtmlContent() ? parsed.getHtmlContent(): parsed.getPlainContent();

                    Date receivedDate = parsed.getMimeMessage().getReceivedDate();
                    log.info("receivedDate: " + receivedDate);
                    log.info("text" + text);
                    log.info("message id: " + parsed.getMimeMessage().getMessageID());
                    String[] header = parsed.getMimeMessage().getHeader("In-Reply-To");
                    String inreplyto = header == null ? "empty": header[0];
                    log.info("in reply to: " + inreplyto);

                    log.info("from " + parsed.getFrom());
                    log.info("subject " + parsed.getSubject());
                    log.info("________________________________");

                    //      sendMessage(parsed.getMimeMessage().getMessageID(), parsed.getFrom(), parsed.getTo().get(0).toString(), parsed.getSubject(), text, parsed.hasHtmlContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                log.info("Message: " + message);
            }
        });
    }
}
