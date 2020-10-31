package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailthreadMapper;
import hu.gdf.balazsbole.domain.repository.EmailthreadRepository;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EmailthreadServiceImpl implements EmailthreadService {

    private final EmailthreadRepository repository;
    private final UserRepository userRepository;
    private final EmailthreadMapper mapper;

    public EmailthreadServiceImpl(EmailthreadRepository repository, UserRepository userRepository, EmailthreadMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EmailthreadEntity createThreadWith(Status status) {
        EmailthreadEntity entity = new EmailthreadEntity();
        entity.setStatus(status);
        return repository.saveAndFlush(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Emailthread> getUnassignedEmailThreads() {
        List<EmailthreadEntity> userIsNull = repository.findAllByUserIsNull();
        return mapper.mapList(userIsNull);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Emailthread> findAllByStatusAndKeycloakUser(UUID keycloakId, Status status) {
        List<EmailthreadEntity> allByStatusAndUser = repository.findAllByStatusAndUser_KeycloakID(status, keycloakId);
        return mapper.mapList(allByStatusAndUser);
    }

    @Override
    public Emailthread updateStatus(UUID emailThreadId, Status status) {
        Optional<EmailthreadEntity> emailthreadEntity = repository.findById(emailThreadId);
        EmailthreadEntity thread = emailthreadEntity.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        thread.setStatus(status);
        return mapper.map(repository.save(thread));
    }

    @Override
    public Emailthread updateUser(UUID emailThreadId, UUID userId) {
        EmailthreadEntity thread = repository.findById(emailThreadId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        thread.setUser(userEntity);

        return mapper.map(repository.save(thread));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Emailthread> findById(UUID emailThreadId) {
        return repository.findById(emailThreadId).map(mapper::map);
    }


}
