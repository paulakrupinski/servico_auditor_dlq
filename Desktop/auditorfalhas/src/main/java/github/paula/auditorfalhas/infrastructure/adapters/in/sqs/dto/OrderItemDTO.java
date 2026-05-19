package github.paula.auditorfalhas.infrastructure.adapters.in.sqs.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Integer sku;

    private Integer amount;

}