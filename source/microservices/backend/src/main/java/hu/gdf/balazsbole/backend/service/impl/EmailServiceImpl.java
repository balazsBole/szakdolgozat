package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.enumeration.Direction;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


/**
 * Service Implementation for managing {@link EmailEntity}.
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailMapper mapper;
    private final EmailRepository repository;
    private final EmailthreadService threadService;


    public EmailServiceImpl(final EmailRepository repository, EmailMapper mapper, EmailthreadService threadService) {
        this.repository = repository;
        this.mapper = mapper;
        this.threadService = threadService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Email> findById(UUID emailId) {
        return repository.findById(emailId).map(mapper::map);
    }

    @Override
    public Email prepareForSending(Email email) {
        String messageID = "<" + getUniqueMessageIDValue() + ">";
        email.getHeader().setMessageId(messageID);
        email.setDirection(Direction.OUT);
        email.setProcessed(LocalDateTime.now());
        email.setRead(true);

        return email;
    }

    private String getUniqueMessageIDValue() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(stringBuilder.hashCode()).append('.').
                append(UUID.randomUUID()).append('.').
                append(System.currentTimeMillis()).
                append("@helpdesk.gdf.localdomain").toString();
    }

    @Override
    @Transactional
    public Email storeNew(Email email, Status status) {
        EmailEntity emailEntity = mapper.map(email);
        Optional<EmailEntity> optionalParent = findParent(emailEntity);
        EmailEntity created;
        if (optionalParent.isPresent()) {
            created = createEmailWithParent(optionalParent.get(), emailEntity, status);
        } else {
            created = threadService.createEmailThreadFor(email);
        }
        return mapper.map(created);
    }

    Optional<EmailEntity> findParent(EmailEntity entity) {
        String parentMessageID = entity.getHeader().getInReplyTo();
        Optional<EmailEntity> parent = repository.findByHeader_MessageId(parentMessageID);
        return parent;
    }

    EmailEntity createEmailWithParent(EmailEntity parent, EmailEntity child, Status status) {
        child.setParentId(parent.getId());
        child.setEmailthread(parent.getEmailthread());
        child.getEmailthread().setStatus(status);
        return repository.saveAndFlush(child);
    }


}
