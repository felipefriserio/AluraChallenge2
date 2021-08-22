package br.com.alura.challenge.backend.controllers.dto.form.filter;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class CategoriaFiltro {

    private Long id;
    private String titulo;
    private String cor;
    private Long categoriaId;
    private Pageable paginacao = PageRequest.of(0,5);
}
