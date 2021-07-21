package br.com.alura.challenge.backend.entity.dto.form;

import br.com.alura.challenge.backend.entity.Video;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonAutoDetect
public class VideoUpdateForm {

    @JsonProperty("id")
    @NotNull(message = "Necessário informar o id para atualizacao")
    private Integer id;

    @JsonProperty("titulo")
    @NotNull(message = "Necessário informar o titulo para atualizacao")
    private String titulo;

    @JsonProperty("descricao")
    @NotNull(message = "Necessário informar a descricao para atualizacao")
    private String descricao;

    @JsonProperty("url")
    @NotNull(message = "Necessário informar a url id para atualizacao")
    private String url;

    @Override
    public String toString() {
        return "VideoUpdateForm{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Video paraVideo() {
        Video video = new Video();
        video.setId(this.id);
        video.setDescricao(this.descricao);
        video.setTitulo(this.titulo);
        video.setUrl(this.url);

        return video;
    }
}
