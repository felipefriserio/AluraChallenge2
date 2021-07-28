package br.com.alura.challenge.backend.service.validacoes.video.incluir;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Order(value = 1)
@Service
public class ValidaPreenchimentoDoVideo implements ValidacaoParaIncluirVideo {

    @Override
    public void validar(Video video) {
        if (video == null) {
            String mensagem = "Video nao pode ser nulo na inclusao";
            throw new ValidacaoException(mensagem);
        }

        List<String> errosDeValidacao = validarCampos(video);
        if (!errosDeValidacao.isEmpty()) {
            String mensagem = "Preenchimento invalido do video= " + video +
                              " validacoes= " + errosDeValidacao;
            throw new ValidacaoException(mensagem);
        }
    }

    private List<String> validarCampos(Video video) {
        List<String> errosDeValidacao = new ArrayList<>();

        String titulo    = video.getTitulo();
        String descricao = video.getDescricao();
        String url       = video.getUrl();

        if (validarSeTextoENuloOuVazio(titulo))
            errosDeValidacao.add("titulo vazio");

        if (validarSeTextoENuloOuVazio(descricao))
            errosDeValidacao.add("descricao vazia");

        if (validarSeTextoENuloOuVazio(url))
            errosDeValidacao.add("url vazio");

        return errosDeValidacao;
    }
}
