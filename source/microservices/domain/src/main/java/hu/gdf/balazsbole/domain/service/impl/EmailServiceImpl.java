package hu.gdf.balazsbole.domain.service.impl;


import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.enumeration.Direction;
import hu.gdf.balazsbole.domain.mapper.EmailMapper;
import hu.gdf.balazsbole.domain.repository.EmailRepository;
import hu.gdf.balazsbole.domain.service.EmailService;
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

    private final EmailRepository repository;
    private final EmailMapper mapper;


    public EmailServiceImpl(final EmailRepository repository, EmailMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Email> findById(final UUID id) {
        log.debug("Find Email : {}", id);
        return repository.findById(id).map(mapper::map);
    }

    @Override
    @Transactional
    public void createEmailWithParent(Email email) {
        EmailEntity entity = mapper.map(email);
        EmailEntity parent = findParent(entity).get();
        entity.setEmailthread(parent.getEmailthread());
        entity.setDirection(Direction.IN);
        entity.setParent(parent);
        repository.saveAndFlush(entity);
    }

    private Optional<EmailEntity> findParent(EmailEntity entity) {
        String parentMessageID = entity.getHeader().getInReplyTo();
        Optional<EmailEntity> parent = repository.findByHeader_MessageId(parentMessageID);
        return parent;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasParent(Email email) {
        if (email.getParent() != null)
            return true;
        return findParent(mapper.map(email)).isPresent();
    }


}
