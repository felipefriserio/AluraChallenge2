package br.com.alura.challenge.backend.entity.dto.form;

import br.com.alura.challenge.backend.entity.Video;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonAutoDetect
public class VideoInsertForm {

    @JsonProperty("titulo")
    @NotNull(message = "Necessário informar a titulo do video")
    @NotEmpty(message = "Necessário informar o titulo do video")
    private String titulo;

    @JsonProperty("descricao")
    @NotNull(message = "Necessário informar a descricao do video")
    @NotEmpty(message = "Necessário informar a descricao do video")
    private String descricao;

    @JsonProperty("url")
    @NotNull(message = "Necessário informar a url do video")
    @NotEmpty(message = "Necessário informar a url do video")
    private String url;

    @Override
    public String toString() {
        return "VideoInsertForm{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Video paraVideo() {
        Video video = new Video();
        video.setDescricao(this.descricao);
        video.setTitulo(this.titulo);
        video.setUrl(this.url);

        return video;
    }
}
