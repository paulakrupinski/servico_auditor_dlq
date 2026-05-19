package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.controller;

import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper.ProdutoDTOMapper;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.ProdutoServicePort;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ProdutoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoControllerAdapter {

    private final ProdutoServicePort produtoServicePort;

    public ProdutoControllerAdapter(ProdutoServicePort produtoServicePort) {
        this.produtoServicePort = produtoServicePort;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoBO produtoBO = ProdutoDTOMapper.toBo(produtoDTO);

        ProdutoBO produtoCriadoBo = produtoServicePort.salvar(produtoBO);

        ProdutoDTO produtoCriadoDTO = ProdutoDTOMapper.toDto(produtoCriadoBo);

        return ResponseEntity.status(201).body(produtoCriadoDTO);
    }
}