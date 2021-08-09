package br.com.alura.challenge.backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RelatorioFilmesPorCategoriaDTO {
    private String titulo;
    private Long videos;
}
