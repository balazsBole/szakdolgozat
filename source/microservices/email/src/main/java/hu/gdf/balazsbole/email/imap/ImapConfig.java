package hu.gdf.balazsbole.email.imap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;

import java.util.Properties;

@Configuration
@EnableAutoConfiguration
public class ImapConfig {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.imap.host}")
    private String host;
    @Value("${spring.mail.imap.port}")
    private String port;
    @Value("${spring.mail.imap.userFlag}")
    private String userFlag;

    @Bean
    public ImapMailReceiver receiver() {
        ImapMailReceiver imapMailReceiver = new ImapMailReceiver(imapUrl());
        imapMailReceiver.setUserFlag(userFlag);
        imapMailReceiver.setSimpleContent(true);
        imapMailReceiver.setShouldDeleteMessages(false);
        imapMailReceiver.setShouldMarkMessagesAsRead(false);
        imapMailReceiver.setJavaMailProperties(javaMailProperties());
        return imapMailReceiver;
    }

    private String imapUrl() {
        return "imap://" + username + ":" + password + "@" + host + ":" + port + "/INBOX";
    }

    private Properties javaMailProperties() {
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.debug", "false");
        return props;
    }

    @Bean
    public ImapIdleChannelAdapter adapter() {
        ImapIdleChannelAdapter adapter = new ImapIdleChannelAdapter(receiver());
        adapter.setAutoStartup(true);
        adapter.setOutputChannelName("incomingEmail");
        return adapter;
    }


}
