package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Direction;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


/**
 * Service Implementation for managing {@link EmailEntity}.
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final AtomicInteger id = new AtomicInteger();
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

    @Override
    public void changeReadStatus(UUID emailId, Boolean read) {
        EmailEntity emailEntity = repository.findById(emailId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        emailEntity.setRead(read);
        repository.saveAndFlush(emailEntity);
    }

    private String getUniqueMessageIDValue() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(stringBuilder.hashCode()).append('.').
                append(id.getAndIncrement()).append('.').
                append(System.currentTimeMillis()).
                append("@helpdesk.localdomain").toString();
    }

    @Override
    @Transactional
    public Email storeNew(Email email) {
        EmailEntity emailEntity = mapper.map(email);

        Optional<EmailEntity> optionalParent = findParent(emailEntity);
        if (optionalParent.isPresent()) {
            emailEntity.setParentId(optionalParent.get().getId());
            emailEntity.setEmailthread(optionalParent.get().getEmailthread());
        } else {
            Optional<EmailEntity> relevantEmail = findRelevantEmail(emailEntity.getHeader().getReferences());
            EmailthreadEntity emailthreadEntity = relevantEmail.map(EmailEntity::getEmailthread)
                    .orElse(threadService.createThreadWith(Status.OPEN));
            emailEntity.setEmailthread(emailthreadEntity);
        }
        if (Direction.IN.equals(emailEntity.getDirection()))
            emailEntity.getEmailthread().setStatus(Status.OPEN);
        return mapper.map(repository.saveAndFlush(emailEntity));
    }

    Optional<EmailEntity> findParent(EmailEntity entity) {
        String parentMessageID = entity.getHeader().getInReplyTo();
        Optional<EmailEntity> parent = repository.findByHeader_MessageId(parentMessageID);
        return parent;
    }

    Optional<EmailEntity> findRelevantEmail(String references) {
        String[] referenceIds = StringUtils.stripToEmpty(references).split("\\s+");
        return Stream.of(referenceIds)
                .map(repository::findByHeader_MessageId).filter(Optional::isPresent)
                .map(Optional::get).findAny();
    }

}
