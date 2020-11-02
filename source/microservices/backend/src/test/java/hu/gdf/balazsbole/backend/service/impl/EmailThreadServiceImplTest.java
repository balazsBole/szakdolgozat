package hu.gdf.balazsbole.backend.service.impl;

import hu.gdf.balazsbole.backend.RunsWithMappers;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.entity.QueueEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.repository.EmailThreadRepository;
import hu.gdf.balazsbole.domain.repository.QueueRepository;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EmailThreadServiceImpl.class})
class EmailThreadServiceImplTest implements RunsWithMappers {

    @MockBean
    private EmailThreadRepository repository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private QueueRepository queueRepository;

    @Autowired
    private EmailThreadServiceImpl service;
    private final UUID uuid = UUID.randomUUID();

    @Test
    void new_emailThread_should_be_store_queue() {
        QueueEntity valueFromRepository = new QueueEntity();
        when(queueRepository.findByEmail(eq("email"))).thenReturn(Optional.of(valueFromRepository));
        service.createThreadFor("email");
        verify(repository, times(1)).save(argThat(entity -> valueFromRepository.equals(entity.getQueue())));
    }

    @Test
    void getUnassignedEmailThreads_should_sreturn_values_from_repository() {
        EmailThreadEntity emailThreadEntity = new EmailThreadEntity();
        emailThreadEntity.setStatus(Status.OPEN);
        when(repository.findAllByUserIsNullAndQueue_Id(eq(uuid))).thenReturn(Collections.singletonList(emailThreadEntity));

        List<EmailThread> result = service.getUnassignedEmailThreadsFor(uuid);
        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());
    }


    @Test
    void findAllByStatusAndUser_should_sreturn_values_from_repository() {
        EmailThreadEntity emailThreadEntity = new EmailThreadEntity();
        emailThreadEntity.setStatus(Status.OPEN);
        when(repository.findAllByStatusAndUser_KeycloakID(eq(Status.OPEN), eq(uuid))).thenReturn(Collections.singletonList(emailThreadEntity));

        List<EmailThread> result = service.findAllByStatusAndKeycloakUser(uuid, Status.OPEN);
        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());
    }

}