package br.com.alura.challenge.backend.controllers.dto.form.filter;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
public class VideoFiltro {
    public VideoFiltro(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private Long categoriaId;
    private Pageable paginacao = PageRequest.of(0,5);
}
