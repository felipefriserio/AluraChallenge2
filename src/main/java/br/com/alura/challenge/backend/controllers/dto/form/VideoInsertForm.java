package br.com.alura.challenge.backend.controllers.dto.form;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonAutoDetect
public class VideoInsertForm {

    @JsonProperty("titulo")
    @NotEmpty(message = "titulo obrigatorio")
    private String titulo;

    @JsonProperty("descricao")
    @NotEmpty(message = "descricao obrigatorio")
    private String descricao;

    @JsonProperty("url")
    @URL(message = "url invalida")
    @NotEmpty(message = "url obrigatorio")
    private String url;

    @JsonProperty("categoriaId")
    private Long categoriaId = 0l;

    @Override
    public String toString() {
        return "VideoInsertForm{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                ", categoriaId=" + categoriaId +
                '}';
    }

    public Video paraVideo() {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);

        Video video = new Video();
        video.setDescricao(this.descricao);
        video.setTitulo(this.titulo);
        video.setUrl(this.url);
        video.setCategoria(categoria);

        return video;
    }
}
