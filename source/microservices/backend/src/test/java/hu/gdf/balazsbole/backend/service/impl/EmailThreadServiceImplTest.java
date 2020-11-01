package hu.gdf.balazsbole.backend.service.impl;

import hu.gdf.balazsbole.backend.RunsWithMappers;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.repository.EmailThreadRepository;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
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

    @Autowired
    private EmailThreadServiceImpl service;

    @Test
    void new_emailThread_should_be_store_status() {
        service.createThreadWith(Status.OPEN);
        verify(repository, times(1)).save(argThat(entity -> Status.OPEN.equals(entity.getStatus())));
    }

    @Test
    void getUnassignedEmailThreads_should_sreturn_values_from_repository() {
        EmailThreadEntity emailThreadEntity = new EmailThreadEntity();
        emailThreadEntity.setStatus(Status.OPEN);
        when(repository.findAllByUserIsNull()).thenReturn(Collections.singletonList(emailThreadEntity));

        List<EmailThread> result = service.getUnassignedEmailThreads();
        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());
    }


    @Test
    void findAllByStatusAndUser_should_sreturn_values_from_repository() {
        final var id = UUID.randomUUID();
        EmailThreadEntity emailThreadEntity = new EmailThreadEntity();
        emailThreadEntity.setStatus(Status.OPEN);
        when(repository.findAllByStatusAndUser_KeycloakID(eq(Status.OPEN), eq(id))).thenReturn(Collections.singletonList(emailThreadEntity));

        List<EmailThread> result = service.findAllByStatusAndKeycloakUser(id, Status.OPEN);
        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());
    }

}