package github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.repository;

import github.paula.auditorfalhas.application.ports.out.persistence.AuditErrorRepositoryPort;
import github.paula.auditorfalhas.core.domain.bo.AuditErrorBO;
import github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.entity.AuditErrorEntity;
import github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.jpa.AuditErrorJpaRepository;
import github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.mapper.AuditErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditErrorRepositoryAdapter
        implements AuditErrorRepositoryPort {

    private final AuditErrorJpaRepository jpaRepository;

    private final AuditErrorMapper mapper;

    @Override
    public AuditErrorBO salvar(AuditErrorBO auditErrorBO) {

        AuditErrorEntity entity =
                mapper.toEntity(auditErrorBO);

        AuditErrorEntity savedEntity =
                jpaRepository.save(entity);

        return mapper.toBO(savedEntity);
    }
}