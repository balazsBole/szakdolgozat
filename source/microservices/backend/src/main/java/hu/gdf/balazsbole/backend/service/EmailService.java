package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.Email;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface EmailService {

    @Transactional
    Email storeNew(Email email);

    @Transactional(readOnly = true)
    Optional<Email> findById(UUID emailId);

    Email prepareForSending(Email email);

    void changeReadStatus(UUID emailId, Boolean read);
}
