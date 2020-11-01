package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    @Transactional
    User getUserFrom(Authentication authentication);

    @Transactional(readOnly = true)
    List<User> searchAutoComplete(String username);
}
