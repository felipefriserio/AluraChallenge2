package br.com.alura.challenge.backend.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RelatorioFilmesPorCategoriaDTO {
    private String titulo;
    private Long videos;
}
