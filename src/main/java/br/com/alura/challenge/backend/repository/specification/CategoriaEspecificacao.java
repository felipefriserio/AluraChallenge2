package br.com.alura.challenge.backend.repository.specification;

import br.com.alura.challenge.backend.entity.dto.form.filter.CategoriaFiltro;
import org.springframework.data.jpa.domain.Specification;

public class CategoriaEspecificacao implements Especificacao{

    public CategoriaEspecificacao(CategoriaFiltro filtro) {
        this.filtro = filtro;
        montar();
    }

    private final CategoriaFiltro filtro;
    private Specification id;
    private Specification titulo;
    private Specification cor;


    private Specification id(Long id) {
        if (id == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    private Specification titulo(String titulo) {
        if (titulo == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%");
    }

    private Specification cor(String cor) {
        if (cor == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("cor"), "%" + cor + "%");
    }

    @Override
    public Specification queryAnd() {
        return Specification.where(id)
                            .and(titulo)
                            .and(cor);
    }

    private void montar() {
        this.id          = id(filtro.getId());
        this.titulo      = titulo(filtro.getTitulo());
        this.cor         = cor(filtro.getCor());
    }
}
