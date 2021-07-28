package br.com.alura.challenge.backend.service.validacoes.categoria.incluir;

import br.com.alura.challenge.backend.entity.Categoria;
import org.springframework.stereotype.Service;

public interface ValidacaoParaIncluirCategoria {
    void validar(Categoria categoria);
    default boolean validarSeTextoENuloOuVazio(String campo) {
        return campo.isBlank();
    }
}
