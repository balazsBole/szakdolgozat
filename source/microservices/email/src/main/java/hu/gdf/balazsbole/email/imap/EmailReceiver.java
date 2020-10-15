package hu.gdf.balazsbole.email.imap;

import hu.gdf.balazsbole.domain.mapper.MimeMessageMapper;
import hu.gdf.balazsbole.email.kafka.EmailKafkaProducer;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class EmailReceiver {

    private final EmailKafkaProducer producer;
    private final MimeMessageMapper mapper;

    public EmailReceiver(EmailKafkaProducer producer, MimeMessageMapper mapper) {
        this.producer = producer;
        this.mapper = mapper;
    }

    @ServiceActivator(inputChannel = "incomingEmail")
    public void receiveMail(Message<MimeMessage> message) {
        EmailProtocolValue value = mapper.mapValue(message.getPayload());
        log.info("Email received from: {}, subject: {}, with messageId: {}", value.getFrom(), value.getSubject(), value.getMessageId());
        EmailProtocolKey key = mapper.mapKey(message.getPayload());

        producer.sendMessage(key, value);
    }

}
