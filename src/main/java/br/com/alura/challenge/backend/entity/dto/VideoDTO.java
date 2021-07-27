package br.com.alura.challenge.backend.entity.dto;

import br.com.alura.challenge.backend.entity.Video;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VideoDTO {

    public VideoDTO(Video video) {
        this.id = video.getId();
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
    }

    private Long id;
    private String titulo;
    @JsonProperty(value = "description")
    private String descricao;
    private String url;
}
