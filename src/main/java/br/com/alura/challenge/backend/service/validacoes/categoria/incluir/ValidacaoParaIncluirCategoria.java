package br.com.alura.challenge.backend.service.validacoes.categoria.incluir;

import br.com.alura.challenge.backend.entity.Categoria;

public interface ValidacaoParaIncluirCategoria {
    void validar(Categoria categoria);
    default boolean validarSeTextoENuloOuVazio(String campo) {
        return campo.isBlank();
    }
}
