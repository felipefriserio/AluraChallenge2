package br.com.alura.challenge.backend.service.validacoes.video;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class ValidaPreenchimentoDoVideoTest {

    private ValidacaoVideo validaPreenchimentoDoVideo = new ValidaPreenchimentoDoVideo();

    @Test
    void devePassarSemLancarExcecao() {
        Video video = mockarVideo();
        validaPreenchimentoDoVideo.validar(video);
    }

    @Test
    void deveRetornarExcecaoAoReceberVideoNulo() {
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            validaPreenchimentoDoVideo.validar(null);
        });

        String mensagemEsperada = "video nulo";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoSemTitulo() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = mockarVideo();
            video.setTitulo("");
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemEsperada = "titulo vazio";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoTituloNulo() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = mockarVideo();
            video.setTitulo(null);
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemEsperada = "titulo vazio";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoSemDescricao() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = mockarVideo();
            video.setDescricao("");
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemEsperada = "descricao vazia";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoDescricaoNulo() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = mockarVideo();
            video.setDescricao(null);
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemEsperada = "descricao vazia";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoSemUrl() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = mockarVideo();
            video.setUrl("");
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemEsperada = "url vazio";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoUrlNulo() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = mockarVideo();
            video.setUrl("");
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemEsperada = "url vazio";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_VideoVazio() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Video video = new Video();
            validaPreenchimentoDoVideo.validar(video);
        });

        String mensagemRecebida  = exception.getMessage();
        String mensagemUrl       = "url vazio";
        String mensagemDescricao = "descricao vazia";
        String mensagemTitulo    = "titulo vazio";

        assertTrue(mensagemRecebida.contains(mensagemUrl));
        assertTrue(mensagemRecebida.contains(mensagemDescricao));
        assertTrue(mensagemRecebida.contains(mensagemTitulo));
    }

    private Video mockarVideo() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        Video video = new Video(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }
}
