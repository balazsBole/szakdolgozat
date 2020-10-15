package hu.gdf.balazsbole.domain.service;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface EmailthreadService {

    @Transactional
    void createEmailThreadFor(Email email);

}
