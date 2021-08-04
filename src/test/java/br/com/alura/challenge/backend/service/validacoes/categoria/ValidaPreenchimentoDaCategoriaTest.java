package br.com.alura.challenge.backend.service.validacoes.categoria;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class ValidaPreenchimentoDaCategoriaTest {

    private ValidacaoCategoria validaCategoria = new ValidaPreenchimentoDaCategoria();

    @Test
    void deveSeguirSemLancarExcecao() {
        Categoria categoria = mockarUmaCategoria();
        validaCategoria.validar(categoria);
    }

    @Test
    void deveRetornarExcecao_CategoriaNulo() {
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            validaCategoria.validar(null);
        });

        String mensagemEsperada = "categoria nula";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_CategoriaSemTitulo() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Categoria categoria = mockarUmaCategoria();
            categoria.setTitulo("");
            validaCategoria.validar(categoria);
        });

        String mensagemEsperada = "titulo vazio";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_CategoriaTituloNulo() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Categoria categoria = mockarUmaCategoria();
            categoria.setTitulo(null);
            validaCategoria.validar(categoria);
        });

        String mensagemEsperada = "titulo vazio";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_CategoriaCorVazia() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Categoria categoria = mockarUmaCategoria();
            categoria.setCor("");
            validaCategoria.validar(categoria);
        });

        String mensagemEsperada = "cor vazia";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_CategoriaCorNula() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Categoria categoria = mockarUmaCategoria();
            categoria.setCor(null);
            validaCategoria.validar(categoria);
        });

        String mensagemEsperada = "cor vazia";
        String mensagemRecebida = exception.getMessage();

        assertTrue(mensagemRecebida.contains(mensagemEsperada));
    }

    @Test
    void deveRetornarExcecaoValidacao_CategoriaVazia() {
        RuntimeException exception = assertThrows(ValidacaoException.class, () -> {
            Categoria categoria = new Categoria();
            validaCategoria.validar(categoria);
        });

        String mensagemRecebida  = exception.getMessage();
        String mensagemTitulo    = "titulo vazio";
        String mensagemCor       = "cor vazia";

        assertTrue(mensagemRecebida.contains(mensagemTitulo));
        assertTrue(mensagemRecebida.contains(mensagemCor));
    }

    private Categoria mockarUmaCategoria() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        return categoria;
    }
}
