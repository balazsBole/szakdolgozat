package hu.gdf.balazsbole.backend.kafka;

import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.enumeration.Direction;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailProtocolMapper;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IncomingEmailObserver {

    private final EmailProtocolMapper mapper;
    private final EmailService service;

    public IncomingEmailObserver(EmailProtocolMapper mapper, EmailService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @KafkaListener(topics = "${spring.kafka.topic.emailIn}", groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.enable:}")
    public void receiveOutgoingEmails(final ConsumerRecord<EmailProtocolKey, EmailProtocolValue> record) {
        log.info("New kafka message received. Partition: {}, Offset: {}, TS: {}", record.partition(), record.offset(), record.timestamp());

        Email email = mapper.map(record.value());
        email.setDirection(Direction.IN);
        service.storeNew(email, Status.OPEN);

    }

}
