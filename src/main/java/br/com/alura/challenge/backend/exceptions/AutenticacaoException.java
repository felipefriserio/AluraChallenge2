package br.com.alura.challenge.backend.exceptions;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException() {
        super();
    }

    public AutenticacaoException(String mensagemDeErro){
        super(mensagemDeErro);
    }

    public AutenticacaoException(String mensagemDeErro, Throwable causa){
        super(mensagemDeErro, causa);
    }
}
