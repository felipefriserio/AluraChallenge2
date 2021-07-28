package br.com.alura.challenge.backend.entity.dto.form;

import br.com.alura.challenge.backend.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonAutoDetect
public class CategoriaUpdateForm {

    @JsonProperty("id")
    @NotNull(message = "Necessário informar o id para atualizacao")
    private Long id;

    @JsonProperty("titulo")
    @NotEmpty(message = "Necessário informar o titulo para atualizacao")
    private String titulo;

    @JsonProperty("cor")
    @NotEmpty(message = "Necessário informar a cor para atualizacao")
    private String cor;

    @Override
    public String toString() {
        return "CategoriaUpdateForm{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cor='" + cor + '\'' +
                '}';
    }

    public Categoria paraCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setTitulo(titulo);
        categoria.setCor(cor);

        return categoria;
    }
}
