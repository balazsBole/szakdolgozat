package hu.gdf.balazsbole.email.kafka;

import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.service.EmailService;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutgoingEmailObserver {

    private final EmailService service;
    private final EmailMapper mapper;

    public OutgoingEmailObserver(EmailService service, EmailMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @KafkaListener(topics = "${spring.kafka.topic.emailOut}", groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.enable:}")
    public void receiveOutgoingEmails(final ConsumerRecord<EmailProtocolKey, EmailProtocolValue> record) {
        log.info("New kafka message received. Partition: {}, Offset: {}, TS: {}",
                record.partition(), record.offset(), record.timestamp());

        final var value = record.value();
        if (null == value) {
            log.error("Error, Empty message! Partition: {}, Offset: {}, TS: {}", record.partition(), record.offset(), record.timestamp());
            return;
        }

        try {
            final var email = mapper.map(value);

            service.createEmailWithParent(email);
        //todo: send email instead of store

        } catch (final Throwable e) {
            log.error("Error while store record!", e);
        }
    }

}
