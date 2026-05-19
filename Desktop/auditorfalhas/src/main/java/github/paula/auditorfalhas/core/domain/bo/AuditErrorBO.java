package github.paula.auditorfalhas.core.domain.bo;

import github.paula.auditorfalhas.core.domain.enums.SeverityEnum;
import github.paula.auditorfalhas.core.domain.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AuditErrorBO {

    private UUID errorId;

    private String queueName;

    private String payload;

    private String timestamp;

    private StatusEnum status;

    private SeverityEnum severity;

}