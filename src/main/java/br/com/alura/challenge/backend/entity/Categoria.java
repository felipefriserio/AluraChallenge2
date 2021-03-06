package br.com.alura.challenge.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categorias")
public class Categoria {

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria(Long id, String titulo, String cor) {
        this.id = id;
        this.titulo = titulo;
        this.cor = cor;
    }

    public Categoria(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "titulo")
    private String titulo;

    @NotEmpty
    @Column(name = "cor")
    private String cor;

    @OneToMany(mappedBy = "categoria")
    private Set<Video> videos = new HashSet<>();

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cor='" + cor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return id.equals(categoria.id) && titulo.equals(categoria.titulo) && cor.equals(categoria.cor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, cor);
    }
}
