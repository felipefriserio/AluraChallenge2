package br.com.alura.challenge.backend.controllers.dto.paginacao;

import lombok.Getter;

@Getter
public class PaginacaoBase {

    protected Long totalDeItens;
    protected Integer paginaAtual;
    protected Integer totalDePaginas;
    protected Integer quantidadeDeItensPorPagina;
}
