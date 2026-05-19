package github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.jpa;

import github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.entity.AuditErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditErrorJpaRepository
        extends JpaRepository<AuditErrorEntity, UUID> {
}