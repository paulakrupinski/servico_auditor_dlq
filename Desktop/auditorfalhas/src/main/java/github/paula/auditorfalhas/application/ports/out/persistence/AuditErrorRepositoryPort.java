package github.paula.auditorfalhas.application.ports.out.persistence;

import github.paula.auditorfalhas.core.domain.bo.AuditErrorBO;

public interface AuditErrorRepositoryPort {

    AuditErrorBO salvar(AuditErrorBO auditErrorBO);

}