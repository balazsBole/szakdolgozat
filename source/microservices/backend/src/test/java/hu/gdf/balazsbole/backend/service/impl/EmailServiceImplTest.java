package hu.gdf.balazsbole.backend.service.impl;

import hu.gdf.balazsbole.backend.RunsWithMappers;
import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.dto.Header;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.entity.HeaderEntity;
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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    }


    private void createEmailWithParent(EmailEntity parent, EmailEntity child) {
        child.setEmailthread(parent.getEmailthread());
        child.setParent(parent);
        repository.saveAndFlush(child);
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
    void should_create_emailthread_if_parent_not_exist() {
        Email email = new Email();
        email.setHeader(new Header());

        service.storeNew(email);
        verify(threadService).createEmailThreadFor(email);
    }

    @Test
    void should_create_email_if_parent_exist() {
        service.storeNew(mapper.map(emailEntity));
        verify(repository).saveAndFlush(argThat(entity -> emailEntity.getHeader().getMessageId().equals(entity.getHeader().getMessageId())));
    }

    @Test
    void should_set_parent_toEntity() {
        assertNull(emailEntity.getEmailthread());
        service.storeNew(mapper.map(emailEntity));
        verify(repository).saveAndFlush(argThat(savedEntity ->
                parentEntity.equals(savedEntity.getParent()) &&
                        savedEntity.getEmailthread() != null));
    }

    private EmailEntity createEmail(String mesageId, String inReplyTo) {
        EmailEntity emailEntity = new EmailEntity();
        HeaderEntity header = new HeaderEntity();
        emailEntity.setHeader(header);
        header.setMessageId(mesageId);
        header.setInReplyTo(inReplyTo);
        return emailEntity;
    }
}