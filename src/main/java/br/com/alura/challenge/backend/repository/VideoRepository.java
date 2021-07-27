package br.com.alura.challenge.backend.repository;

import br.com.alura.challenge.backend.entity.Video;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends PagingAndSortingRepository<Video, Long>,
                                         JpaSpecificationExecutor<Video>,
                                         CrudRepository<Video, Long> {
}
