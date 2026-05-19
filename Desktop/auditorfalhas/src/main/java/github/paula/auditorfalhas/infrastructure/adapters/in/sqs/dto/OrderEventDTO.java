package github.paula.auditorfalhas.infrastructure.adapters.in.sqs.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderEventDTO {

    private String zipCode;

    private Integer customerId;

    private List<OrderItemDTO> orderItems;

    private String origin;

    private String occurredAt;

}