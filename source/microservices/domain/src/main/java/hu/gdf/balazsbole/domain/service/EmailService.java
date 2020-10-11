package hu.gdf.balazsbole.domain.service;

import hu.gdf.balazsbole.domain.dto.Email;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface EmailService {

    Optional<Email> findById(@NotNull UUID id);

    @Transactional
    void createEmailWithParent(Email email);
}
