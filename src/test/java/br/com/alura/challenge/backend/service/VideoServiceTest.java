package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.controllers.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService service;

    @Mock
    private VideoRepository repository;

    @Test
    void deveRetornarPaginacaoDeVideos_listar() {
        Page<Video> videosPaginados = mockarVideosPaginados();
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(videosPaginados);

        Page<Video> paginacao = service.listar(new VideoFiltro());

        assertNotNull(paginacao);
        verify(repository, times(1)).findAll(any(), any(Pageable.class));
    }

    @Test
    void deveRetornarVideo_encontrarPorId() {
        Video videoMockado = mockarVideo();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(videoMockado));

        Video video = service.encontrarPorId(any(Long.class));

        assertNotNull(video);
        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void deveRetornarExcecaoAoBuscarVideoIdNulo_encontrarPorId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Video video = service.encontrarPorId(null);
            verify(repository, times(0)).findById(any(Long.class));
        });
    }

    @Test
    void deveRetornarEntidadeNaoEncontradaExceptionAoBuscarVideoInexistente_encontrarPorId() {
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> {
            when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
            Video video = service.encontrarPorId(any(Long.class));
            verify(repository, times(1)).findById(any(Long.class));
        });
    }

   /* @Test
    void deveRetornarVideoAtualizado_atualizar(){
        Video video = mockarVideo();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(video));

        Categoria categoriaAtualizada = mockarUmaCategoria();
        categoriaAtualizada.setId(2l);

        Video videoAtualizado = mockarVideo();
        videoAtualizado.setTitulo("titulo atualizado");
        videoAtualizado.setDescricao("descricao atualizada");
        videoAtualizado.setUrl("http://www.atualizado.com.br");
        videoAtualizado.setCategoria(categoriaAtualizada);

        video = service.atualizar(videoAtualizado);

        assertEquals(video, videoAtualizado);
        assertEquals(categoriaAtualizada.getId(), video.getCategoria().getId());
    }

    @Test
    void deveRetornarExcecaoSeVideoForNulo_atualizar(){
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.atualizar(null);
        });

        String mensagemEsperada = "video nulo";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }*/

    @Test
    void deveDeletarSeIdValido_deletar(){
        Video video = mockarVideo();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(video));
        service.deletar(any(Long.class));

        verify(repository, times(1)).findById(any(Long.class));
        verify(repository, times(1)).delete(any(Video.class));
    }

    @Test
    void deveRetornarExcecaoSeIdNulo_deletar() {
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.deletar((Long) null);
        });

        String mensagemEsperada = "id nulo";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoEntidadeQuandoVideoNaoEncontrado_deletar() {
        RuntimeException exception = assertThrows(EntidadeNaoEncontradaException.class, () -> {
            service.deletar(any(Long.class));
        });

        String mensagemEsperada = "nao encontrado";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    private Page<Video> mockarVideosPaginados() {
        Video video = mockarVideo();
        List<Video> videos = Arrays.asList(video);
        Page<Video> videosPaginados = new PageImpl<>(videos);
        return videosPaginados;
    }

    private Video mockarVideo() {
        Categoria categoria = mockarUmaCategoria();
        Video video = new Video(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }

    private Categoria mockarUmaCategoria() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        return categoria;
    }
}
