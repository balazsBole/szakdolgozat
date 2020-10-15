package hu.gdf.balazsbole.email.smtp;


import hu.gdf.balazsbole.email.mapper.MimeMessageMapper;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class EmailSender {

    private final JavaMailSender mailSender;
    private final MimeMessageMapper mapper;

    public EmailSender(JavaMailSender mailSender, MimeMessageMapper mapper) {
        this.mailSender = mailSender;
        this.mapper = mapper;
    }

    public void send(EmailProtocolValue emailProtocolValue) {
        MimeMessage message = mapper.map(emailProtocolValue, mailSender.createMimeMessage());
        log.info("Sending message to: {}, subject: {}, with messageId: {}",
                emailProtocolValue.getTo(), emailProtocolValue.getSubject(), emailProtocolValue.getMessageId());
        mailSender.send(message);
    }
}
