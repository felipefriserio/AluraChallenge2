package br.com.alura.challenge.backend.entity.dto.form;

import br.com.alura.challenge.backend.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonAutoDetect
public class CategoriaInsertForm {

    @JsonProperty("titulo")
    @NotEmpty(message = "O titulo e obrigatorio")
    private String titulo;

    @JsonProperty("cor")
    @NotEmpty(message = "A cor e obrigatoria")
    private String cor;

    @Override
    public String toString() {
        return "CategoriaInsertForm{" +
                "titulo='" + titulo + '\'' +
                ", cor='" + cor + '\'' +
                '}';
    }

    public Categoria paraCategoria() {
        Categoria categoria = new Categoria();
        categoria.setTitulo(titulo);
        categoria.setCor(cor);

        return categoria;
    }
}
