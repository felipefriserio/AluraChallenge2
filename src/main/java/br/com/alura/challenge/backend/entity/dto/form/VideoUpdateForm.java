package br.com.alura.challenge.backend.entity.dto.form;

import br.com.alura.challenge.backend.entity.Video;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonAutoDetect
public class VideoUpdateForm {

    @JsonProperty("id")
    @NotNull(message = "id obrigatorio")
    private Long id;

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
