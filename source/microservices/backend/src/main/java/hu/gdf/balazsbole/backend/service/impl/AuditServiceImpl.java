package hu.gdf.balazsbole.backend.service.impl;


import hu.gdf.balazsbole.backend.service.AuditService;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import hu.gdf.balazsbole.domain.mapper.EmailThreadMapper;
import hu.gdf.balazsbole.domain.repository.EmailThreadRepository;
import hu.gdf.balazsbole.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditReader auditReader;
    private final EmailThreadMapper emailThreadMapper;
    private final UserRepository userRepository;
    private final EmailThreadRepository emailThreadRepository;


    public AuditServiceImpl(AuditReader auditReader, EmailThreadMapper emailThreadMapper, UserRepository userRepository, EmailThreadRepository emailThreadRepository) {
        this.auditReader = auditReader;
        this.emailThreadMapper = emailThreadMapper;
        this.userRepository = userRepository;
        this.emailThreadRepository = emailThreadRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmailThread> historyOfEmailThreadBy(UUID emailThreadId) {
        return emailThreadMapper.mapList(emailThreadRepository.getLastModificationApproveForBP(emailThreadId));
//
//        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(EmailThreadEntity.class, true, true);
//        q.add(AuditEntity.id().eq(emailThreadId));
//        List<EmailThreadEntity> emailThreads = q.getResultList();
//        return emailThreadMapper.mapList(emailThreads);

    }

    @Override
    public List<EmailThread> currentStatusOfEmailThreadByPreviousOwner(UUID keycloakId) {
        UserEntity userEntity = userRepository.findByKeycloakID(keycloakId).get();


        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(EmailThreadEntity.class, false, true);
//        q.add(AuditEntity.id().eq(emailThreadId));

        List<EmailThreadEntity> emailThreads = q.getResultList();

        return emailThreadMapper.mapList(emailThreads);
    }
}
