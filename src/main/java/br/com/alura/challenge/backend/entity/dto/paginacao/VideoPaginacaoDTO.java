package br.com.alura.challenge.backend.entity.dto.paginacao;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.VideoDTO;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VideoPaginacaoDTO extends PaginacaoBase {

    public VideoPaginacaoDTO(Page<Video> paginacao){
        setVideos(paginacao.getContent());

        this.totalDeItens = paginacao.getTotalElements();
        this.quantidadeDeItensPorPagina = paginacao.getSize();
        this.totalDePaginas = paginacao.getTotalPages();
        this.paginaAtual = paginacao.getNumber();
    }

    private final List<VideoDTO> videos = new ArrayList<>();

    private void setVideos(List<Video> videos) {
        for ( Video video : videos ) {
            VideoDTO dto = new VideoDTO(video);
            this.videos.add(dto);
        }
    }
}


