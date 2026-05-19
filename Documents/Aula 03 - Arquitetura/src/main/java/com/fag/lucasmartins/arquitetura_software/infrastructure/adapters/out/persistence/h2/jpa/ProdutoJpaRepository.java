package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.jpa;


import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, Integer> {
}
