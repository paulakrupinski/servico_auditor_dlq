package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto;

public class ProdutoDTO {

    private String nome;

    private Integer estoque;

    private double preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
