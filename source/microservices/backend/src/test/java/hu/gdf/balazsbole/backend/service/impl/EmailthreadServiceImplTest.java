package hu.gdf.balazsbole.backend.service.impl;

import hu.gdf.balazsbole.backend.RunsWithMappers;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.repository.EmailthreadRepository;
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
@SpringBootTest(classes = {EmailthreadServiceImpl.class})
class EmailthreadServiceImplTest implements RunsWithMappers {

    @MockBean
    private EmailthreadRepository repository;

    @Autowired
    private EmailthreadServiceImpl service;

    @Test
    void new_emailthread_should_be_open() {
        service.createEmailThreadFor(new Email());
        verify(repository, times(1)).saveAndFlush(argThat(entity -> Status.OPEN.equals(entity.getStatus())));
    }

    @Test
    void service_should_save_and_flush_email() {
        Email emailToSave = new Email();
        service.createEmailThreadFor(emailToSave);
        verify(repository, times(1)).saveAndFlush(argThat(entity -> !entity.getEmails().isEmpty()));
    }


    @Test
    void getUnassignedEmailThreads_should_sreturn_values_from_repository() {
        EmailthreadEntity emailthreadEntity = new EmailthreadEntity();
        emailthreadEntity.setStatus(Status.OPEN);
        when(repository.findAllByUserIsNull()).thenReturn(Collections.singletonList(emailthreadEntity));

        List<Emailthread> result = service.getUnassignedEmailThreads();
        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());
    }


    @Test
    void findAllByStatusAndUser_should_sreturn_values_from_repository() {
        final var id = UUID.randomUUID();
        EmailthreadEntity emailthreadEntity = new EmailthreadEntity();
        emailthreadEntity.setStatus(Status.OPEN);
        when(repository.findAllByStatusAndUser_KeycloakID(eq(Status.OPEN), eq(id))).thenReturn(Collections.singletonList(emailthreadEntity));

        List<Emailthread> result = service.findAllByStatusAndUser(id, Status.OPEN);
        assertEquals(1, result.size());
        assertEquals(Status.OPEN, result.get(0).getStatus());
    }

}