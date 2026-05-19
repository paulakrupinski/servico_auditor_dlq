package github.paula.auditorfalhas.infrastructure.adapters.in.sqs.listener;

import github.paula.auditorfalhas.application.ports.in.service.DlqAuditServicePort;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DlqListenerAdapter {

    private final DlqAuditServicePort servicePort;

    @SqsListener("${aws.sqs.dlq-name}")
    public void receive(String payload) {

        servicePort.processarMensagem(payload);

    }
}