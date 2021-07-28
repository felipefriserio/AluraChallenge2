package br.com.alura.challenge.backend.exceptions;

public class ValidacaoException extends RuntimeException {

    public ValidacaoException() {
        super();
    }

    public ValidacaoException(String mensagemDeErro){
        super(mensagemDeErro);
    }

    public ValidacaoException(String mensagemDeErro, Throwable causa){
        super(mensagemDeErro, causa);
    }
}
