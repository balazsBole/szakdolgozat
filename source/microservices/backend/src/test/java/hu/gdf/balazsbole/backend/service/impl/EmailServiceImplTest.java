package hu.gdf.balazsbole.backend.service.impl;

import hu.gdf.balazsbole.backend.RunsWithMappers;
import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.dto.Header;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.entity.HeaderEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EmailServiceImpl.class})
class EmailServiceImplTest implements RunsWithMappers {

    @MockBean
    private EmailRepository repository;

    @MockBean
    private EmailthreadService threadService;

    @Autowired
    private EmailServiceImpl service;

    @Autowired
    private EmailMapper mapper;

    private EmailEntity emailEntity;
    private EmailEntity parentEntity;


    @BeforeEach
    void init() {
        emailEntity = createEmail("test_mesage_id", "test_parent_message_id");
        parentEntity = createEmail("test_parent_message_id", null);
        parentEntity.setEmailthread(new EmailthreadEntity());

        when(repository.findByHeader_MessageId(eq("test_parent_message_id"))).thenReturn(Optional.of(parentEntity));
        when(repository.findByHeader_MessageId(eq(""))).thenReturn(Optional.empty());
    }

    @Test
    void findById_should_return_result_from_service() {
        final var id = UUID.randomUUID();

        when(repository.findById(eq(id))).thenReturn(Optional.of(emailEntity));
        Optional<Email> email = service.findById(id);
        verify(repository).findById(id);

        assertTrue(email.isPresent());
        assertEquals(emailEntity.getHeader().getMessageId(), email.get().getHeader().getMessageId());
    }

    @Test
    void should_search_for_parent_with_messageId() {
        Optional<EmailEntity> parent = service.findParent(emailEntity);
        verify(repository).findByHeader_MessageId("test_parent_message_id");
        assertTrue(parent.isPresent());
    }

    @Test
    void should_not_find_parent_with_wrong_messageId() {
        EmailEntity wrong = createEmail(null, "wrong");
        Optional<EmailEntity> parent = service.findParent(wrong);
        verify(repository).findByHeader_MessageId("wrong");
        assertTrue(parent.isEmpty());
    }

    @Test
    void should_create_emailthread_if_no_relevant_email_exist() {
        Email email = new Email();
        email.setHeader(new Header());

        service.storeNew(email);
        verify(threadService).createThreadWith(any(Status.class));
    }

    @Test
    void should_create_email_if_parent_exist() {
        service.storeNew(mapper.map(emailEntity));
        verify(repository).save(argThat(entity -> emailEntity.getHeader().getMessageId().equals(entity.getHeader().getMessageId())));
    }


    @Test
    void if_reference_contains_email_then_email_thread_should_be_the_same() {
        emailEntity.getHeader().setReferences(emailEntity.getHeader().getInReplyTo());
        emailEntity.getHeader().setInReplyTo("");
        service.storeNew(mapper.map(emailEntity));

        verify(repository).save(argThat(entity -> emailEntity.getHeader().getMessageId().equals(entity.getHeader().getMessageId())));
        verify(repository).save(argThat(entity -> parentEntity.getEmailthread().equals(entity.getEmailthread())));
        verify(repository).save(argThat(entity -> entity.getParentId() == null));
    }


    @Test
    void should_set_parent_toEntity() {
        assertNull(emailEntity.getEmailthread());
        service.storeNew(mapper.map(emailEntity));
        verify(repository).save(argThat(savedEntity ->
                savedEntity.getEmailthread() != null));
        verify(repository).save(argThat(savedEntity ->
                parentEntity.getId().equals(savedEntity.getParentId())));
    }

    private EmailEntity createEmail(String mesageId, String inReplyTo) {
        EmailEntity emailEntity = new EmailEntity();
        HeaderEntity header = new HeaderEntity();
        emailEntity.setId(UUID.randomUUID());
        emailEntity.setHeader(header);
        header.setMessageId(mesageId);
        header.setInReplyTo(inReplyTo);
        return emailEntity;
    }

}