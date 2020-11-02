package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.mapper.AuthenticationMapper;
import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.entity.QueueEntity;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.mapper.UserMapper;
import hu.gdf.balazsbole.domain.repository.QueueRepository;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Service Implementation for managing {@link hu.gdf.balazsbole.domain.entity.UserEntity}.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final AuthenticationMapper authenticationMapper;
    private final UserRepository repository;
    private final QueueRepository queueRepository;

    public UserServiceImpl(UserMapper mapper, AuthenticationMapper authenticationMapper, UserRepository repository, QueueRepository queueRepository) {
        this.mapper = mapper;
        this.authenticationMapper = authenticationMapper;
        this.repository = repository;
        this.queueRepository = queueRepository;
    }

    @Override
    @Transactional
    public User getUserFrom(Authentication authentication) {
        UserEntity fromAuthentication = authenticationMapper.map(authentication);

        Optional<UserEntity> optionalUserEntity = repository.findByKeycloakID(fromAuthentication.getKeycloakID());
        if (optionalUserEntity.isEmpty()) {
            repository.save(fromAuthentication);
        }
        return mapper.map(optionalUserEntity.orElse(fromAuthentication));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchAutoComplete(UUID queueId, String username) {
        String cleanedUpValue = StringUtils.trimToNull(username);
        if (null == cleanedUpValue || cleanedUpValue.length() < 1) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        }
        return mapper.mapList(repository.findAllByQueue_IdAndUsernameIgnoreCaseContaining(queueId, cleanedUpValue));
    }

    @Override
    public void updateQueueFor(UUID userKeycloakUUID, UUID queueId) {
        Optional<UserEntity> optionalUserEntity = repository.findByKeycloakID(userKeycloakUUID);
        Optional<QueueEntity> optionalQueueEntity = queueRepository.findById(queueId);
        if (optionalUserEntity.isEmpty() || optionalQueueEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setQueue(optionalQueueEntity.get());
        repository.save(userEntity);
    }
}
