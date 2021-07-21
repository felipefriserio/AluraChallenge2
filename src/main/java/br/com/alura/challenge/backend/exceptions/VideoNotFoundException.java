package br.com.alura.challenge.backend.exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String mensagem) {
        super(mensagem);
    }

    public VideoNotFoundException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
