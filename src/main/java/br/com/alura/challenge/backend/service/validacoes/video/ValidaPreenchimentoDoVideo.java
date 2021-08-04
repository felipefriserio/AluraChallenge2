package br.com.alura.challenge.backend.service.validacoes.video;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Order(value = 1)
@Service
public class ValidaPreenchimentoDoVideo implements ValidacaoVideo {

    public void validar(Video video) {
        if (video == null) {
            String mensagem = "video nulo";
            throw new IllegalArgumentException(mensagem);
        }

        List<String> errosDeValidacao = validarCampos(video);
        if (!errosDeValidacao.isEmpty()) {
            String mensagem = "preenchimento invalido do video= " + video +
                    " validacoes= " + errosDeValidacao;
            throw new ValidacaoException(mensagem);
        }
    }

    private List<String> validarCampos(Video video) {
        List<String> errosDeValidacao = new ArrayList<>();

        String titulo    = video.getTitulo();
        String descricao = video.getDescricao();
        String url       = video.getUrl();

        if (StringUtils.isEmpty(titulo))
            errosDeValidacao.add("titulo vazio");

        if (StringUtils.isEmpty(descricao))
            errosDeValidacao.add("descricao vazia");

        if (StringUtils.isEmpty(url))
            errosDeValidacao.add("url vazio");

        return errosDeValidacao;
    }
}
