package br.com.alura.challenge.backend.controllers.dto.form.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaFiltro extends FiltroBase {

    private Long id;
    private String titulo;
    private String cor;
    private Long categoriaId;

    @Override
    public String toString() {
        return "CategoriaFiltro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cor='" + cor + '\'' +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
