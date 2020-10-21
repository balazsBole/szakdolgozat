package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EmailthreadService {

    @Transactional
    void createEmailThreadFor(Email email);

    @Transactional(readOnly = true)
    List<Emailthread> getUnassignedEmailThreads();

    @Transactional(readOnly = true)
    List<Emailthread> findAllByStatusAndUser(UUID keycloakId, Status status);
}
