package br.com.alura.challenge.backend.entity.dto.form;

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
    @NotEmpty(message = "Necessário informar o titulo do video")
    private String titulo;

    @JsonProperty("descricao")
    @NotEmpty(message = "Necessário informar a descricao do video")
    private String descricao;

    @JsonProperty("url")
    @URL(message = "Necessário informar uma url valida")
    @NotEmpty(message = "Necessário informar a url do video")
    private String url;

    @JsonProperty("categoriaId")
    private Long categoriaId;

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
