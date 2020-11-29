package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    @Transactional
    void storeUser(User user);

    @Transactional(readOnly = true)
    List<User> searchAutoComplete(UUID queueId, String username);

    @Transactional
    void updateQueueFor(UUID userUUID, UUID queueId);

    @Transactional(readOnly = true)
    Optional<User> getUser(UUID userId);
}
