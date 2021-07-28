package br.com.alura.challenge.backend.exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public EntidadeNaoEncontradaException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
