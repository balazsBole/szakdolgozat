package hu.gdf.balazsbole.email.kafka;

import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailKafkaProducer {

    private final KafkaTemplate<EmailProtocolKey, EmailProtocolValue> kafkaTemplate;

    @Value("${spring.kafka.topic.emailIn}")
    private String topicName;


    public EmailKafkaProducer(KafkaTemplate<EmailProtocolKey, EmailProtocolValue> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(EmailProtocolKey key, EmailProtocolValue value) {
        log.info("Sending email to {} kafkatopic. Message key: {}", topicName, key.getMessageId());
        kafkaTemplate.send(topicName, key, value);
    }


}


