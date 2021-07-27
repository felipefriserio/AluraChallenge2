package br.com.alura.challenge.backend.entity.dto.form.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoFiltro extends FiltroBase {

    private Long id;
    private String titulo;
    private String descricao;
    private String url;

    @Override
    public String toString() {
        return "VideoFiltro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
