package br.com.alura.challenge.backend.controllers.dto;

import br.com.alura.challenge.backend.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaDTO {

    public CategoriaDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.titulo = categoria.getTitulo();
        this.cor = categoria.getCor();
    }

    private Long id;
    private String titulo;
    private String cor;
    private String mensagem;
}
