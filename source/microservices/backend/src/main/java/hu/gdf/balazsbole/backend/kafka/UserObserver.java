package hu.gdf.balazsbole.backend.kafka;

import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.mapper.UserProtocolMapper;
import hu.gdf.balazsbole.kafka.user.UserProtocolKey;
import hu.gdf.balazsbole.kafka.user.UserProtocolValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserObserver {

    private final UserProtocolMapper mapper;
    private final UserService service;

    public UserObserver(UserProtocolMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @KafkaListener(topics = "${spring.kafka.topic.user}", groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.enable:}")
    public void receiveIncomingEmails(final ConsumerRecord<UserProtocolKey, UserProtocolValue> record) {
        log.info("New kafka message received. Partition: {}, Offset: {}, TS: {}", record.partition(), record.offset(), record.timestamp());

        User user = mapper.map(record.value());
        log.info(record.value().toString());
        service.storeUser(user);

    }

}
