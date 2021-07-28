package br.com.alura.challenge.backend.entity.dto.paginacao;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.dto.CategoriaDTO;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoriaPaginacaoDTO extends PaginacaoBase {

    public CategoriaPaginacaoDTO(Page<Categoria> paginacao){
        setCategorias(paginacao.getContent());

        this.totalDeItens = paginacao.getTotalElements();
        this.quantidadeDeItensPorPagina = paginacao.getSize();
        this.totalDePaginas = paginacao.getTotalPages();
        this.paginaAtual = paginacao.getNumber();
    }

    private final List<CategoriaDTO> categorias = new ArrayList<>();

    public void setCategorias(List<Categoria> categorias) {
        for ( Categoria categoria : categorias ) {
            CategoriaDTO dto = new CategoriaDTO(categoria);
            this.categorias.add(dto);
        }
    }
}

