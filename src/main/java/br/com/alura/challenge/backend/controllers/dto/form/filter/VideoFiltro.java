package br.com.alura.challenge.backend.controllers.dto.form.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoFiltro extends FiltroBase {
    public VideoFiltro(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private Long categoriaId;

    @Override
    public String toString() {
        return "VideoFiltro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
