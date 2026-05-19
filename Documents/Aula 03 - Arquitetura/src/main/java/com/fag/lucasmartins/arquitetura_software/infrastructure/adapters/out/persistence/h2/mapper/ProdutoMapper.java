package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper;

import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;

public class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static ProdutoEntity toEntity(ProdutoBO produtoBO) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(produtoBO.getNome());
        produtoEntity.setEstoque(produtoBO.getEstoque());
        produtoEntity.setPreco(produtoBO.getPreco());
        produtoEntity.setPrecoFinal(produtoBO.getPrecoFinal());

        return produtoEntity;
    }

    public static ProdutoBO toBO(ProdutoEntity produtoEntity) {
        ProdutoBO produtoBO = new ProdutoBO();
        produtoBO.setNome(produtoEntity.getNome());
        produtoBO.setEstoque(produtoEntity.getEstoque());
        produtoBO.setPreco(produtoEntity.getPreco());
        produtoBO.setPrecoFinal(produtoEntity.getPrecoFinal());

        return produtoBO;
    }
}
