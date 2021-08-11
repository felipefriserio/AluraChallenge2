package br.com.alura.challenge.backend.repository;

import br.com.alura.challenge.backend.controllers.dto.RelatorioFilmesPorCategoriaDTO;
import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class VideoRepositoryTest {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveRetornarRelatorioPreenchido() {
        Categoria acao = new Categoria("acao", "#00000");
        Categoria comedia = new Categoria("comedia", "#00000");

        acao = em.persist(acao);
        comedia = em.persist(comedia);

        Video primeiroVideo = new Video("Titulo","descricao","http://www.site.com.br", acao);
        Video segundoVideo = new Video("Titulo 2","descricao 2","http://www.site.com.br", acao);
        Video terceiroVideo = new Video("Titulo 3","descricao 3","http://www.site.com.br", comedia);

        em.persist(primeiroVideo);
        em.persist(segundoVideo);
        em.persist(terceiroVideo);

        RelatorioFilmesPorCategoriaDTO relatorioAcao = new RelatorioFilmesPorCategoriaDTO("acao", 2l);
        RelatorioFilmesPorCategoriaDTO relatorioComedia = new RelatorioFilmesPorCategoriaDTO("comedia", 1l);

        List<RelatorioFilmesPorCategoriaDTO> relatorio = repository.relatorioDeFilmesPorCategoria();
        assertNotNull(relatorio);
        assertEquals(relatorio.size(), 2);
        assertTrue(relatorio.contains(relatorioAcao));
        assertTrue(relatorio.contains(relatorioComedia));
    }

    @Test
    void deveRetornarRelatorioVazio() {
        List<RelatorioFilmesPorCategoriaDTO> relatorio = repository.relatorioDeFilmesPorCategoria();
        assertNotNull(relatorio);
        assertEquals(relatorio.size(), 0);
    }
}
