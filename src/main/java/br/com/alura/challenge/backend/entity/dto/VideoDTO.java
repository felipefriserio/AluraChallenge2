package br.com.alura.challenge.backend.entity.dto;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {

    public VideoDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public VideoDTO(Video video) {
        this.id = video.getId();

        Categoria categoria = video.getCategoria();
        this.categoriaId = categoria.getId();

        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
    }

    private Long id;
    private Long categoriaId;
    private String titulo;
    private String descricao;
    private String url;
    private String mensagem;

    public List<VideoDTO> listarVideos(List<Video> videos){
        List<VideoDTO> videosDto = new ArrayList<>();
        for (Video video : videos) {
            videosDto.add(new VideoDTO(video));
        }
        return videosDto;
    }
}
