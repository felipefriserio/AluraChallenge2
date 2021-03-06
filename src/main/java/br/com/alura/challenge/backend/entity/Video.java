package br.com.alura.challenge.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
public class Video {

    public Video(String titulo,
                 String descricao,
                 String url,
                 Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoria = categoria;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "titulo")
    private String titulo;

    @NotEmpty
    @Column(name = "descricao")
    private String descricao;

    @URL
    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Categoria categoria = new Categoria();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return id.equals(video.id) && titulo.equals(video.titulo) && descricao.equals(video.descricao) && url.equals(video.url) && Objects.equals(categoria, video.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descricao, url, categoria);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
