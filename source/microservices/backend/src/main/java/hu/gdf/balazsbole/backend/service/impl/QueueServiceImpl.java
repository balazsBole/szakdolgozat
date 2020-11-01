package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.QueueService;
import hu.gdf.balazsbole.domain.dto.Queue;
import hu.gdf.balazsbole.domain.entity.QueueEntity;
import hu.gdf.balazsbole.domain.mapper.QueueMapper;
import hu.gdf.balazsbole.domain.repository.QueueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing {@link QueueEntity}.
 */
@Service
@Slf4j
public class QueueServiceImpl implements QueueService {

    private final QueueRepository repository;
    private final QueueMapper mapper;

    public QueueServiceImpl(QueueRepository repository, QueueMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Queue> findAll() {
        return mapper.mapList(repository.findAll());
    }

}
