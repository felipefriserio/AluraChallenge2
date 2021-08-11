package br.com.alura.challenge.backend.repository;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.controllers.dto.RelatorioFilmesPorCategoriaDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends PagingAndSortingRepository<Video, Long>,
                                         JpaSpecificationExecutor<Video>,
                                         CrudRepository<Video, Long> {

    @Query(value = "select " +
                   " new br.com.alura.challenge.backend.controllers.dto.RelatorioFilmesPorCategoriaDTO(c.titulo, count(v.id)) " +
                   " from Video v" +
                   " join v.categoria c" +
                   " group by c.titulo")
    List<RelatorioFilmesPorCategoriaDTO> relatorioDeFilmesPorCategoria();
}
