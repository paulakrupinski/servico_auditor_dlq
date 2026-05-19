package github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.mapper;

import github.paula.auditorfalhas.core.domain.bo.AuditErrorBO;
import github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.entity.AuditErrorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditErrorMapper {

    public AuditErrorEntity toEntity(AuditErrorBO bo) {

        AuditErrorEntity entity = new AuditErrorEntity();

        entity.setErrorId(bo.getErrorId());
        entity.setQueueName(bo.getQueueName());
        entity.setPayload(bo.getPayload());
        entity.setTimestamp(bo.getTimestamp());
        entity.setStatus(bo.getStatus());
        entity.setSeverity(bo.getSeverity());

        return entity;
    }

    public AuditErrorBO toBO(AuditErrorEntity entity) {

        return AuditErrorBO.builder()
                .errorId(entity.getErrorId())
                .queueName(entity.getQueueName())
                .payload(entity.getPayload())
                .timestamp(entity.getTimestamp())
                .status(entity.getStatus())
                .severity(entity.getSeverity())
                .build();
    }
}