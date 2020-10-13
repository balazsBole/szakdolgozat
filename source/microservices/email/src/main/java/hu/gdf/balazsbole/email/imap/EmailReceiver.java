package hu.gdf.balazsbole.email.imap;

import hu.gdf.balazsbole.domain.mapper.EmailProtocolMapper;
import hu.gdf.balazsbole.email.kafka.EmailKafkaProducer;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class EmailReceiver {

    private final EmailKafkaProducer producer;
    private final EmailProtocolMapper mapper;

    public EmailReceiver(EmailKafkaProducer producer, EmailProtocolMapper mapper) {
        this.producer = producer;
        this.mapper = mapper;
    }

    @ServiceActivator(inputChannel = "incomingEmail")
    public void receiveMail(Message<MimeMessage> message) {
        EmailProtocolKey key = mapper.mapKey(message.getPayload());
        EmailProtocolValue value = mapper.mapValue(message.getPayload());
        producer.sendMessage(key, value);
    }

}
