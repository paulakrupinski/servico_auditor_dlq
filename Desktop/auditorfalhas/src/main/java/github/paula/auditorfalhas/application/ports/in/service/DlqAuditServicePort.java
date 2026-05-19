package github.paula.auditorfalhas.application.ports.in.service;

public interface DlqAuditServicePort {

    void processarMensagem(String payload);

}