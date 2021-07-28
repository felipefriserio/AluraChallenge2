package br.com.alura.challenge.backend.service.validacoes.video.incluir;

import br.com.alura.challenge.backend.entity.Video;

public interface ValidacaoParaIncluirVideo {
    void validar(Video video);
    default boolean validarSeTextoENuloOuVazio(String campo) {
        return campo.isBlank();
    }
}
