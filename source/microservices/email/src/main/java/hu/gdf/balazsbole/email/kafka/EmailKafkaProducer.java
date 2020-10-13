package hu.gdf.balazsbole.email.kafka;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.mapper.EmailProtocolMapper;
import hu.gdf.balazsbole.kafka.email.EmailProtocol;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailKafkaProducer {

    @Value("${spring.kafka.topic.emailIn}")
    private String topicName;

    private final KafkaTemplate<EmailProtocolKey, EmailProtocolValue> kafkaTemplate;
    private final EmailProtocolMapper mapper;


    public EmailKafkaProducer(KafkaTemplate<EmailProtocolKey, EmailProtocolValue> kafkaTemplate, EmailProtocolMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }


    public void sendMessage(MimeMessageParser parser) {
        try {
            kafkaTemplate.send(topicName, mapper.mapKey(parser), mapper.mapValue(parser));
        } catch (Exception e) {
            log.error("Error, Message parsing! {}", parser);
        }
    }


}


