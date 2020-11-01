package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.mapper.AuthenticationMapper;
import hu.gdf.balazsbole.backend.service.UserService;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.mapper.UserMapper;
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

    @Override
    public List<User> searchAutoComplete(String username) {
        String cleanedUpValue = StringUtils.trimToNull(username);
        if (null == cleanedUpValue || cleanedUpValue.length() < 3) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        }
        return mapper.mapList(repository.findAllByUsernameIgnoreCaseContaining(cleanedUpValue));
    }
}
