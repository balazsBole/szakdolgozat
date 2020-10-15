package hu.gdf.balazsbole.domain.service;

import hu.gdf.balazsbole.domain.dto.Email;
import org.springframework.transaction.annotation.Transactional;

public interface EmailthreadService {

    @Transactional
    void createEmailThreadFor(Email email);

}
