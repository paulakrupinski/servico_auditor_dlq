package github.paula.auditorfalhas.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.paula.auditorfalhas.application.ports.in.service.DlqAuditServicePort;
import github.paula.auditorfalhas.application.ports.out.persistence.AuditErrorRepositoryPort;
import github.paula.auditorfalhas.core.domain.bo.AuditErrorBO;
import github.paula.auditorfalhas.core.domain.enums.SeverityEnum;
import github.paula.auditorfalhas.core.domain.enums.StatusEnum;
import github.paula.auditorfalhas.core.domain.exceptions.DomainException;
import github.paula.auditorfalhas.infrastructure.adapters.in.sqs.dto.OrderEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DlqAuditService implements DlqAuditServicePort {

    private final AuditErrorRepositoryPort repositoryPort;

    private final ObjectMapper objectMapper;

    @Override
    public void processarMensagem(String payload) {

        try {

            log.info("Mensagem recebida da DLQ! Processando...");

            OrderEventDTO dto =
                    objectMapper.readValue(payload, OrderEventDTO.class);

            int totalItens = dto.getOrderItems()
                    .stream()
                    .mapToInt(item -> item.getAmount())
                    .sum();

            SeverityEnum severity = calcularSeveridade(totalItens);

            log.info("Total de itens: {} | Severidade calculada: {}", totalItens, severity);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            AuditErrorBO auditErrorBO =
                    AuditErrorBO.builder()
                            .errorId(UUID.randomUUID())
                            .queueName(dto.getOrigin())
                            .payload(payload)
                            .timestamp(timestamp)
                            .status(StatusEnum.PENDING_ANALYSIS)
                            .severity(severity)
                            .build();

            repositoryPort.salvar(auditErrorBO);

            log.info("Mensagem salva com sucesso no banco! ID: {} | Severidade: {}", auditErrorBO.getErrorId(), severity);

        } catch (Exception e) {

            log.error("Erro ao processar mensagem da DLQ: {}", e.getMessage());

            throw new DomainException(
                    "Erro ao processar mensagem da DLQ."
            );
        }
    }

    private SeverityEnum calcularSeveridade(int totalItens) {

        if (totalItens > 100) {
            return SeverityEnum.HIGH;
        }

        if (totalItens >= 50) {
            return SeverityEnum.MEDIUM;
        }

        return SeverityEnum.LOW;
    }
}