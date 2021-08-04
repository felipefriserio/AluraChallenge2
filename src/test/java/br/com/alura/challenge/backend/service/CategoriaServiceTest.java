package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.form.filter.CategoriaFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService service;

    @Mock
    private CategoriaRepository repository;

    @Test
    void deveRetornarPaginacaoDeVideos_listar() {
        Page<Categoria> categoriasPaginadas = mockarCategoriasPaginadas();
        when(repository.findAll(any(), any(Pageable.class))).thenReturn(categoriasPaginadas);

        Page<Categoria> paginacao = service.listar(new CategoriaFiltro());

        assertNotNull(paginacao);
        verify(repository, times(1)).findAll(any(), any(Pageable.class));
    }

    @Test
    void deveRetornarCategoria_encontrarPorId() {
        Categoria categoriaMockado = mockarUmaCategoria();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(categoriaMockado));

        Categoria categoria = service.encontrarPorId(any(Long.class));

        assertNotNull(categoria);
        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void deveRetornarEntidadeNaoEncontradaExceptionAoBuscarCategoriaInexistente_encontrarPorId() {
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> {
            when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
            Categoria categoria = service.encontrarPorId(any(Long.class));
            verify(repository, times(1)).findById(any(Long.class));
        });
    }

    @Test
    void deveRetornarExcecaoAoBuscarCategoriaIdNulo_encontrarPorId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Categoria categoria = service.encontrarPorId(null);
            verify(repository, times(0)).findById(any(Long.class));
        });
    }

    /*@Test
    void deveRetornarCategoriaAtualizada_atualizar() {
        Categoria categoria = mockarUmaCategoria();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(categoria));

        Categoria categoriaAtualizada = mockarUmaCategoria();
        categoriaAtualizada.setTitulo("titulo atualizado");
        categoriaAtualizada.setCor("cor atualizada");

        categoria = service.atualizar(categoriaAtualizada);

        assertEquals(categoria, categoriaAtualizada);
        assertEquals(categoriaAtualizada.getId(), categoria.getId());
    }

    @Test
    void deveRetornarExcecaoSeCategoriaForNulo_atualizar(){
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.atualizar(null);
        });

        String mensagemEsperada = "video nulo";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }*/

    @Test
    void deveDeletarSeIdValido_deletar() {
        Categoria categoria = mockarUmaCategoria();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(categoria));
        service.deletar(any(Long.class));

        verify(repository, times(1)).findById(any(Long.class));
        verify(repository, times(1)).delete(any(Categoria.class));
    }

    @Test
    void deveRetornarExcecaoSeIdNulo_deletar(){
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.deletar((Long) null);
        });

        String mensagemEsperada = "id nulo";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoEntidadeQuandoCategoriaNaoEncontrado_deletar() {
        RuntimeException exception = assertThrows(EntidadeNaoEncontradaException.class, () -> {
            service.deletar(any(Long.class));
        });

        String mensagemEsperada = "nao encontrado";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    private Page<Categoria> mockarCategoriasPaginadas() {
        Categoria categoria = mockarUmaCategoria();
        List<Categoria> categorias = Arrays.asList(categoria);
        return new PageImpl<>(categorias);
    }

    private Page<Video> mockarVideosPaginados() {
        Video video = mockarUmVideo();
        List<Video> videos = Arrays.asList(video);
        Page<Video> videosPaginados = new PageImpl<>(videos);
        return videosPaginados;
    }

    private Categoria mockarUmaCategoria() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        return categoria;
    }

    private Video mockarUmVideo() {
        Categoria categoria = mockarUmaCategoria();
        Video video = new Video(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
