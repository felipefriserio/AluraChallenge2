package br.com.alura.challenge.backend.controllers.dto.form;

import br.com.alura.challenge.backend.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoriaUpdateForm {

    @JsonProperty("id")
    @NotNull(message = "id obrigatorio")
    private Long id;

    @JsonProperty("titulo")
    @NotEmpty(message = "titulo obrigatorio")
    private String titulo;

    @JsonProperty("cor")
    @NotEmpty(message = "cor obrigatoria")
    private String cor;

    public Categoria paraCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setTitulo(titulo);
        categoria.setCor(cor);

        return categoria;
    }
}
