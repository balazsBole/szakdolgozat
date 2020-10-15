package hu.gdf.balazsbole.email.kafka;

import hu.gdf.balazsbole.email.smtp.EmailSender;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutgoingEmailObserver {

    private final EmailSender emailSender;

    public OutgoingEmailObserver(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @KafkaListener(topics = "${spring.kafka.topic.emailOut}", groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.enable:}")
    public void receiveOutgoingEmails(final ConsumerRecord<EmailProtocolKey, EmailProtocolValue> record) {
        log.info("New kafka message received. Partition: {}, Offset: {}, TS: {}", record.partition(), record.offset(), record.timestamp());
        emailSender.send(record.value());
    }

}
