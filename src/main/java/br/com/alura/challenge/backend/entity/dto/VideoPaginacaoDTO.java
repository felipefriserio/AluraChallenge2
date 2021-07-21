package br.com.alura.challenge.backend.entity.dto;

import br.com.alura.challenge.backend.entity.Video;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VideoPaginacaoDTO {

    public VideoPaginacaoDTO(){}
    public VideoPaginacaoDTO(Page<Video> paginacao){
        setVideos(paginacao.getContent());

        this.totalDeItens = paginacao.getTotalElements();
        this.quantidadeDeItensPorPagina = paginacao.getSize();
        this.totalDePaginas = paginacao.getTotalPages();
        this.paginaAtual = paginacao.getNumber();
    }

    private final List<VideoDTO> videos = new ArrayList<>();
    private Long totalDeItens;
    private Integer paginaAtual;
    private Integer totalDePaginas;
    private Integer quantidadeDeItensPorPagina;

    public void setVideos(List<Video> videos) {
        for ( Video video : videos ) {
            VideoDTO dto = new VideoDTO(video);
            this.videos.add(dto);
        }
    }
}


