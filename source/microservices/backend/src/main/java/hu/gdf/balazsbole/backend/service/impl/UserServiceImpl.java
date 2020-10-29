package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.mapper.AuthenticationMapper;
import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.mapper.UserMapper;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link hu.gdf.balazsbole.domain.entity.UserEntity}.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final AuthenticationMapper authenticationMapper;
    private final UserRepository repository;

    public UserServiceImpl(UserMapper mapper, AuthenticationMapper authenticationMapper, UserRepository repository) {
        this.mapper = mapper;
        this.authenticationMapper = authenticationMapper;
        this.repository = repository;
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
}
