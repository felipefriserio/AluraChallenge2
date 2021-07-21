package br.com.alura.challenge.backend.entity.dto.form.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class FiltroBase {

    // Atributos da paginacao
    private Sort.Direction direcao = Sort.Direction.ASC;
    private String campoOrdenacao = "id";
    private int pagina;
    private int quantidadeDeItensPorPagina = 10;
    private final int LIMITE_ITENS_PAGINACAO = 100;

    public Pageable getPaginacao() {
        return PageRequest.of(this.pagina,
                              this.quantidadeDeItensPorPagina,
                              getOrdenacao()
        );
    }

    public Sort getOrdenacao() {
        return Sort.by(direcao, campoOrdenacao);
    }

    public int getQuantidadeDeItensPorPagina() {
        return Math.min(quantidadeDeItensPorPagina, LIMITE_ITENS_PAGINACAO);
    }
}