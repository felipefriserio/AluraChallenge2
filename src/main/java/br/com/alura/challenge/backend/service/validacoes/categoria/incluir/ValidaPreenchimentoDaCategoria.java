package br.com.alura.challenge.backend.service.validacoes.categoria.incluir;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Order(value = 1)
@Service
public class ValidaPreenchimentoDaCategoria implements ValidacaoParaIncluirCategoria {

    @Override
    public void validar(Categoria categoria) {
        if (categoria == null){
            String mensagem = "Categoria nao pode ser nula na inclusao";
            throw new ValidacaoException(mensagem);
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

        if (validarSeTextoENuloOuVazio(categoria.getTitulo()))
            errosDeValidacao.add("titulo vazio");

        if (validarSeTextoENuloOuVazio(categoria.getCor()))
            errosDeValidacao.add("cor vazia");

        return errosDeValidacao;
    }
}
