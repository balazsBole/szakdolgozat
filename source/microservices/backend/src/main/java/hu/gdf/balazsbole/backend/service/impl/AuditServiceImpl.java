package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.AuditService;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.EmailThreadAudit;
import hu.gdf.balazsbole.domain.entity.EmailThreadAuditEntity;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.mapper.EmailThreadAuditMapper;
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
    private final UserRepository userRepository;
    private final EmailThreadAuditRepository auditRepository;
    private final EmailThreadRepository emailThreadRepository;

    public AuditServiceImpl(EmailThreadAuditMapper auditMapper, UserRepository userRepository, EmailThreadAuditRepository auditRepository, EmailThreadRepository emailThreadRepository) {
        this.auditMapper = auditMapper;
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
    public List<EmailThread> emailThreadsRelatedToUser(UUID keycloakId) {
        UserEntity userEntity = userRepository.findByKeycloakID(keycloakId).get();
        List<UUID> relatedIds = auditRepository.getThreadIdsRelatedToUser(userEntity.getId());
        return auditMapper.mapListToEmailThread(emailThreadRepository.findAllById(relatedIds));
    }
}
