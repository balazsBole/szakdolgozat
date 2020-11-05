package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.AuditService;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.EmailThreadAudit;
import hu.gdf.balazsbole.domain.entity.EmailThreadAuditEntity;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.mapper.EmailThreadAuditMapper;
import hu.gdf.balazsbole.domain.mapper.EmailThreadMapper;
import hu.gdf.balazsbole.domain.repository.EmailThreadAuditRepository;
import hu.gdf.balazsbole.domain.repository.EmailThreadRepository;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final EmailThreadAuditMapper auditMapper;
    private final EmailThreadMapper emailThreadMapper;
    private final UserRepository userRepository;
    private final EmailThreadAuditRepository auditRepository;
    private final EmailThreadRepository emailThreadRepository;

    public AuditServiceImpl(EmailThreadAuditMapper auditMapper, EmailThreadMapper emailThreadMapper, UserRepository userRepository, EmailThreadAuditRepository auditRepository, EmailThreadRepository emailThreadRepository) {
        this.auditMapper = auditMapper;
        this.emailThreadMapper = emailThreadMapper;
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
        this.emailThreadRepository = emailThreadRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmailThreadAudit> historyOfEmailThreadBy(UUID emailThreadId) {
        List<EmailThreadAuditEntity> allVersionOf = auditRepository.findAllByThreadId(emailThreadId);
        return auditMapper.mapList(allVersionOf);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailThread> emailThreadsRelatedToUser(UUID keycloakId) {
        UserEntity userEntity = userRepository.findByKeycloakID(keycloakId).get();
        List<UUID> relatedIds = auditRepository.getThreadIdsRelatedToUser(userEntity.getId());
        List<EmailThreadEntity> entities = emailThreadRepository.findAllById(relatedIds);
        return auditMapper.mapListToEmailThread(entities);
    }
}
