package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.Email;
import org.springframework.transaction.annotation.Transactional;

public interface EmailService {

    @Transactional
    void storeNew(Email email);
}
