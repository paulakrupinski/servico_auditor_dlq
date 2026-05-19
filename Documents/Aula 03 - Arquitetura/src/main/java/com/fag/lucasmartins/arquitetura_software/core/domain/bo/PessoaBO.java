package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class PessoaBO {

    private UUID id;

    private String nomeCompleto;

    private String cpf;

    private LocalDate dataNascimento;

    private String email;

    private String telefone;

    public void prepararCadastro() {
        gerarIdSeNulo();
        validarNomeCompleto();
        validarMaioridade();
        validarCpf();
        validarTelefone();
        validarEmail();
    }

    private void gerarIdSeNulo() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    private void validarNomeCompleto() {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new DomainException("Erro: Nome completo e obrigatorio.");
        }
    }

    private void validarMaioridade() {
        if (dataNascimento == null) {
            throw new DomainException("Erro: Data de nascimento e obrigatoria.");
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DomainException("Erro: Data de nascimento invalida.");
        }

        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 18) {
            throw new DomainException("Erro: Cliente deve ter no minimo 18 anos.");
        }
    }

    private void validarCpf() {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DomainException("Erro: CPF e obrigatorio.");
        }

        if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            throw new DomainException("Erro: CPF deve conter exatamente 11 caracteres numericos.");
        }
    }

    private void validarTelefone() {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new DomainException("Erro: Telefone e obrigatorio.");
        }

        if (telefone.length() != 11 || !telefone.matches("\\d{11}")) {
            throw new DomainException("Erro: Telefone deve conter exatamente 11 caracteres numericos.");
        }
    }

    private void validarEmail() {
        if (email == null || email.trim().isEmpty()) {
            throw new DomainException("Erro: E-mail e obrigatorio.");
        }

        if (!email.contains("@")) {
            throw new DomainException("Erro: E-mail invalido.");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}