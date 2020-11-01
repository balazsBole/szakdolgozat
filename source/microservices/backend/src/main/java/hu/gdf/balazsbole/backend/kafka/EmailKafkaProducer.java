package hu.gdf.balazsbole.backend.kafka;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.mapper.EmailProtocolMapper;
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
    private final EmailProtocolMapper mapper;

    @Value("${spring.kafka.topic.emailOutSuffix}")
    private String topicSuffix;


    public EmailKafkaProducer(KafkaTemplate<EmailProtocolKey, EmailProtocolValue> kafkaTemplate, EmailProtocolMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void sendMessage(Email email) {
        EmailProtocolKey key = mapper.mapAvroKey(email);
        EmailProtocolValue value = mapper.mapAvroValue(email);
        String topicName = email.getHeader().getFrom().replaceAll("@", "_") + topicSuffix;

        log.info("Sending email to {} kafkatopic. Message key: {}", topicName, key.getMessageId());
        kafkaTemplate.send(topicName, key, value);
    }


}


