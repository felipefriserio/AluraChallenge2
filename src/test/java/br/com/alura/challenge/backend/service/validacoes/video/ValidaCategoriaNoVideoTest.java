package br.com.alura.challenge.backend.service.validacoes.video;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ValidaCategoriaNoVideo.class)
public class ValidaCategoriaNoVideoTest {

    @MockBean
    private CategoriaService service;

    @Autowired
    private ValidacaoVideo validaCategoria;

    @Test
    void deveRetornarMesmoVideoRecebido() {
        Video video = mockarVideo();
        validaCategoria.validar(video);

        assertNotNull(video);
        assertEquals(mockarVideo(), video);
        assertEquals(mockarVideo().getCategoria(), video.getCategoria());
        verify(service, times(0)).encontrarPorId(any(Long.class));
    }

    @Test
    void deveRetornarVideoComCategoriaPadrao_CategoriaNula() {
        when(service.encontrarPorId(any(Long.class))).thenReturn(mockarUmaCategoria());

        Video video = mockarVideo();
        video.setCategoria(null);

        validaCategoria.validar(video);

        assertNotNull(video);
        assertNotNull(mockarVideo().getCategoria());
        verify(service, times(1)).encontrarPorId(any(Long.class));
    }

    @Test
    void deveRetornarVideoComCategoriaPadrao_CategoriaSemId() {
        when(service.encontrarPorId(any(Long.class))).thenReturn(mockarUmaCategoria());

        Video video = mockarVideo();
        video.getCategoria().setId(0l);

        validaCategoria.validar(video);

        assertNotNull(video);
        assertEquals(video.getCategoria().getId(), mockarVideo().getId());
        verify(service, times(1)).encontrarPorId(any(Long.class));
    }

    private Categoria mockarUmaCategoria() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        return categoria;
    }

    private Video mockarVideo() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        Video video = new Video(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }
}
