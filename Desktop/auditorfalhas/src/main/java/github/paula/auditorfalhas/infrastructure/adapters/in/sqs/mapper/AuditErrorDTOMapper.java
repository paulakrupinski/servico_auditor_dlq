package github.paula.auditorfalhas.infrastructure.adapters.in.sqs.mapper;

import github.paula.auditorfalhas.infrastructure.adapters.in.sqs.dto.OrderEventDTO;
import org.springframework.stereotype.Component;

@Component
public class AuditErrorDTOMapper {

    public OrderEventDTO toDTO(OrderEventDTO dto) {
        return dto;
    }

}