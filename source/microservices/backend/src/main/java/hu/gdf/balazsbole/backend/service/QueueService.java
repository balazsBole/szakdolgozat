package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.Queue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QueueService {

    @Transactional(readOnly = true)
    List<Queue> findAll();
}
