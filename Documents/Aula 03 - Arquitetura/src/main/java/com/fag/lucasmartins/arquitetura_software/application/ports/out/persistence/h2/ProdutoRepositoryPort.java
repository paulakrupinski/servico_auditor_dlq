package com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.h2;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;

public interface ProdutoRepositoryPort {

    ProdutoBO salvar(ProdutoBO produtoBO);
}
