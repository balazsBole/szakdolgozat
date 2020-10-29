package hu.gdf.balazsbole.backend.kafka;

import hu.gdf.balazsbole.backend.RunsWithMappers;
import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.domain.enumeration.Direction;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {IncomingEmailObserver.class})
class IncomingEmailObserverTest implements RunsWithMappers {

    @Autowired
    private IncomingEmailObserver observer;

    @MockBean
    private EmailService service;


    @Test
    void should_store_incoming_direction() {
        observer.receiveOutgoingEmails(new ConsumerRecord<>("topic", 1, 0, new EmailProtocolKey(), new EmailProtocolValue()));
        verify(service).storeNew(argThat(entity -> Direction.IN.equals(entity.getDirection())));
    }
}