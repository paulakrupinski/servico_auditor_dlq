package github.paula.auditorfalhas.infrastructure.adapters.out.persistence.h2.entity;

import github.paula.auditorfalhas.core.domain.enums.SeverityEnum;
import github.paula.auditorfalhas.core.domain.enums.StatusEnum;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "audit_errors")
public class AuditErrorEntity {

    @Id
    private UUID errorId;

    private String queueName;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String timestamp;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private SeverityEnum severity;

    public UUID getErrorId() {
        return errorId;
    }

    public void setErrorId(UUID errorId) {
        this.errorId = errorId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public SeverityEnum getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityEnum severity) {
        this.severity = severity;
    }
}