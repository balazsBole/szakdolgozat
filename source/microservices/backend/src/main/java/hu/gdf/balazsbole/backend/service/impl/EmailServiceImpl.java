package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void storeNew(Email email) {
        EmailEntity emailEntity = mapper.map(email);
        Optional<EmailEntity> optionalParent = findParent(emailEntity);
        if (optionalParent.isPresent()) {
            createEmailWithParent(optionalParent.get(), emailEntity);
        } else
            threadService.createEmailThreadFor(email);
    }

    Optional<EmailEntity> findParent(EmailEntity entity) {
        String parentMessageID = entity.getHeader().getInReplyTo();
        Optional<EmailEntity> parent = repository.findByHeader_MessageId(parentMessageID);
        return parent;
    }

    void createEmailWithParent(EmailEntity parent, EmailEntity child) {
        child.setEmailthread(parent.getEmailthread());
        child.setParent(parent);
        repository.saveAndFlush(child);
    }


}
