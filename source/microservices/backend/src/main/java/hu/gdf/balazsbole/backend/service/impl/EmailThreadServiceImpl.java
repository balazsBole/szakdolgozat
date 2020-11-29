package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.EmailThreadService;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.entity.QueueEntity;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailThreadMapper;
import hu.gdf.balazsbole.domain.repository.EmailThreadRepository;
import hu.gdf.balazsbole.domain.repository.QueueRepository;
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
public class EmailThreadServiceImpl implements EmailThreadService {

    private final EmailThreadRepository repository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;
    private final EmailThreadMapper mapper;

    public EmailThreadServiceImpl(EmailThreadRepository repository, QueueRepository queueRepository, UserRepository userRepository, EmailThreadMapper mapper) {
        this.repository = repository;
        this.queueRepository = queueRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EmailThreadEntity createThreadFor(String email, String description) {
        EmailThreadEntity entity = new EmailThreadEntity();
        entity.setStatus(Status.OPEN);
        entity.setDescription(description);
        entity.setQueue(queueRepository.findByEmail(email).get());
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailThread> getUnassignedEmailThreadsFor(UUID keycloakUUID) {
        QueueEntity relevantQueue = findQueueOfUserKeycloakId(keycloakUUID);
        UUID queueId = relevantQueue.getId();
        List<EmailThreadEntity> userIsNull = repository.findAllByUserIsNullAndQueue_Id(queueId);
        return mapper.mapList(userIsNull);
    }

    private QueueEntity findQueueOfUserKeycloakId(UUID userID) {
        Optional<UserEntity> optional = userRepository.findById(userID);
        if (optional.isEmpty() || null == optional.get().getQueue()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
        }
        return optional.get().getQueue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailThread> findAllByStatusAndUserId(UUID userId, Status status) {
        List<EmailThreadEntity> allByStatusAndUser = repository.findAllByStatusAndUser_Id(status, userId);
        return mapper.mapList(allByStatusAndUser);
    }

    @Override
    public List<EmailThread> findAllByStatusInTheQueueOf(Status status, UUID keycloakId) {
        QueueEntity relevantQueue = findQueueOfUserKeycloakId(keycloakId);
        UUID queueId = relevantQueue.getId();

        List<EmailThreadEntity> allByStatusAndQueue = repository.findAllByQueue_IdAndStatus(queueId, status);
        return mapper.mapList(allByStatusAndQueue);
    }

    @Override
    @Transactional
    public void updateStatus(UUID emailThreadId, Status status) {
        Optional<EmailThreadEntity> emailThreadEntity = repository.findById(emailThreadId);
        EmailThreadEntity thread = emailThreadEntity.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        if (status != thread.getStatus()) {
            thread.setStatus(status);
            repository.save(thread);
        }
    }

    @Override
    @Transactional
    public void updateUser(UUID emailThreadId, String userId) {
        EmailThreadEntity thread = repository.findById(emailThreadId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        UserEntity userEntity = null;
        if (userId != null)
            userEntity = userRepository.findById(UUID.fromString(userId)).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        if (userEntity != thread.getUser()) {
            thread.setUser(userEntity);
            repository.save(thread);
        }
    }

    @Override
    @Transactional
    public void updateQueue(UUID emailThreadId, UUID queueId) {
        EmailThreadEntity thread = repository.findById(emailThreadId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        QueueEntity queueEntity = queueRepository.findById(queueId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        if (queueEntity != thread.getQueue()) {
            thread.setQueue(queueEntity);
            repository.save(thread);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailThread> findById(UUID emailThreadId) {
        return repository.findById(emailThreadId).map(mapper::map);
    }


}
