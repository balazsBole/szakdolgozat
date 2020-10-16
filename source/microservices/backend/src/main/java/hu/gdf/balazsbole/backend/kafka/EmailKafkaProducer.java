package hu.gdf.balazsbole.backend.kafka;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
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
    private final EmailMapper mapper;

    @Value("${spring.kafka.topic.emailOut}")
    private String topicName;


    public EmailKafkaProducer(KafkaTemplate<EmailProtocolKey, EmailProtocolValue> kafkaTemplate, EmailMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }


    public void sendMessage(Email email) {
        EmailProtocolKey key = mapper.mapAvroKey(email);
        EmailProtocolValue value = mapper.mapAvroValue(email);

        log.info("Sending email to {} kafkatopic. Message key: {}", topicName, key.getMessageId());
        kafkaTemplate.send(topicName, key, value);
    }


}


