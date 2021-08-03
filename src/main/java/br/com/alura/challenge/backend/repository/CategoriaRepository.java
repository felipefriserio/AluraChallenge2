package br.com.alura.challenge.backend.repository;

import br.com.alura.challenge.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>,
                                             JpaSpecificationExecutor<Categoria>,
                                             CrudRepository<Categoria, Long> {
}

