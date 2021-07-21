package br.com.alura.challenge.backend.repository.specification;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import org.springframework.data.jpa.domain.Specification;

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


    public Specification<Video> id(Integer id) {
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

    public Specification queryAnd() {
        return Specification.where(id)
                            .and(titulo)
                            .and(descricao)
                            .and(url);
    }

    private void montar() {
        this.id        = id(filtro.getId());
        this.url       = url(filtro.getUrl());
        this.titulo    = titulo(filtro.getTitulo());
        this.descricao = descricao(filtro.getDescricao());
    }
}
