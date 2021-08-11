package br.com.alura.challenge.backend.repository.specification;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.controllers.dto.form.filter.VideoFiltro;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class VideoEspecificacao implements Especificacao {

    public VideoEspecificacao(VideoFiltro filtro) {
        this.filtro = filtro;
        montar();
    }

    private final VideoFiltro filtro;
    private Specification id;
    private Specification titulo;
    private Specification descricao;
    private Specification url;
    private Specification categoriaId;


    public Specification<Video> id(Long id) {
        if (id == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<Video> titulo(String titulo) {
        if (titulo == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%");
    }

    public Specification<Video> descricao(String descricao) {
        if (descricao == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("descricao"), "%" + descricao + "%");
    }

    public Specification<Video> url(String url) {
        if (url == null) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("url"),  "%" + url + "%");
    }

    private Specification categoriaId(Long categoriaId) {
        if (categoriaId == null) return null;
        return (root, query, criteriaBuilder) -> {
            Join<Video, Categoria> join = root.join("categoria", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(join.get("id"), categoriaId));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification queryAnd() {
        return Specification.where(id)
                            .and(titulo)
                            .and(descricao)
                            .and(url)
                            .and(categoriaId);
    }

    private void montar() {
        this.id        = id(filtro.getId());
        this.url       = url(filtro.getUrl());
        this.titulo    = titulo(filtro.getTitulo());
        this.descricao = descricao(filtro.getDescricao());
        this.categoriaId = categoriaId(filtro.getCategoriaId());
    }
}
