package br.com.alura.challenge.backend.service.validacoes.categoria;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Order(value = 1)
@Service
public class ValidaPreenchimentoDaCategoria implements ValidacaoCategoria {

    public void validar(Categoria categoria) {
        if (categoria == null){
            String mensagem = "categoria nula";
            throw new IllegalArgumentException(mensagem);
        }

        List<String> errosDeValidacao  = validarCampos(categoria);
        if (!errosDeValidacao.isEmpty()) {
            String mensagem = "Preenchimento invalido da categoria= " + categoria +
                              " validacoes= " + errosDeValidacao;
            throw new ValidacaoException(mensagem);
        }
    }

    private List<String> validarCampos(Categoria categoria) {
        List<String> errosDeValidacao = new ArrayList<>();

        if (StringUtils.isEmpty(categoria.getTitulo()))
            errosDeValidacao.add("titulo vazio");

        if (StringUtils.isEmpty(categoria.getCor()))
            errosDeValidacao.add("cor vazia");

        return errosDeValidacao;
    }
}
