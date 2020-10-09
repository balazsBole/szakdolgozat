/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hu.gdf.balazsbole.email.imap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.support.DefaultMailHeaderMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.mail.internet.MimeMessage;
import javax.print.attribute.DateTimeSyntax;
import java.util.Date;
import java.util.Properties;

/**
 * @author Oleg Zhurakousky
 * @author Gary Russell
 */
public class GmailInboundImapIdleAdapterTestApp {
    private static Log logger = LogFactory.getLog(GmailInboundImapIdleAdapterTestApp.class);

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
                    logger.info("receivedDate: " + receivedDate);
                    logger.info("text" + text);
                    logger.info("message id: " + parsed.getMimeMessage().getMessageID());
                    String[] header = parsed.getMimeMessage().getHeader("In-Reply-To");
                    String inreplyto = header == null ? "empty": header[0];
                    logger.info("in reply to: " + inreplyto);

                    logger.info("from " + parsed.getFrom());
                    logger.info("subject " + parsed.getSubject());
                    logger.info("________________________________");

              //      sendMessage(parsed.getMimeMessage().getMessageID(), parsed.getFrom(), parsed.getTo().get(0).toString(), parsed.getSubject(), text, parsed.hasHtmlContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                logger.info("Message: " + message);
            }
        });
    }

    public static void sendMessage(String messageID, String to, String from, String subject, String text, boolean htmlMessage) {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.yandex.com");
        sender.setUsername("helpdesk.gdf");
        sender.setPassword("Tha0xi7k");
        sender.setPort(465);
        final String username = "username@gmail.com";
        final String password = "password";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.yandex.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sender.setJavaMailProperties(prop);

        MimeMessage message = sender.createMimeMessage();

// use the true flag to indicate you need a multipart message
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setFrom(from);
        helper.setText(text, htmlMessage);
        helper.setSubject(subject);

        message.setHeader("In-Reply-To", messageID);

            sender.send(message);
            message.getMessageID();
            message.getSentDate();

        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

// let's attach the infamous windows Sample file (this time copied to c:/)
        // FileSystemResource file = new FileSystemResource(new File("c:/Sample.jpg"));
        // helper.addAttachment("CoolImage.jpg", file);


//todo: store message;

    }
}
