package br.com.alura.challenge.backend.exceptions.handler;

import br.com.alura.challenge.backend.entity.dto.ApiExceptionDTO;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.exceptions.ValidacaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ChallengeExceptionHandler {

    @Value("${spring.profiles.active:desconhecido}")
    private String activeProfile;

    @Value("${requisicao.log.completo:false}")
    private boolean mostraLogCompleto;

    @ExceptionHandler(value = {EntidadeNaoEncontradaException.class})
    public ResponseEntity<Object> tratarExcecao(EntidadeNaoEncontradaException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiExceptionDTO apiExceptionDTO = gerarApiExceptionDTO(e, httpStatus, e.getMessage());
        log.error("EntidadeNaoEncontradaException - ", e);
        return new ResponseEntity<>(apiExceptionDTO, httpStatus);
    }

    @ExceptionHandler(value = {ValidacaoException.class})
    public ResponseEntity<Object> tratarExcecao(ValidacaoException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiExceptionDTO apiExceptionDTO = gerarApiExceptionDTO(e, httpStatus, e.getMessage());
        log.error("ValidacaoException - ", e);
        return new ResponseEntity<>(apiExceptionDTO, httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> tratarExcecao(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StringBuilder erros = new StringBuilder();
        for ( ObjectError erroDeValidacao : e.getBindingResult().getAllErrors()) {
            erros.append(" - " + erroDeValidacao.getDefaultMessage()  );
        }
        ApiExceptionDTO apiExceptionDTO = gerarApiExceptionDTO(e, httpStatus, erros.toString());
        log.warn("MethodArgumentNotValidException - ", e);
        return new ResponseEntity<>(apiExceptionDTO, httpStatus);
    }

   @ExceptionHandler(value = {RuntimeException.class})
   public ResponseEntity<Object> tratarExcecao(RuntimeException e) {
       HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
       ApiExceptionDTO apiExceptionDTO = gerarApiExceptionDTO(e, httpStatus, "");
       log.error("RuntimeException - ", e);
       return new ResponseEntity<>(apiExceptionDTO, httpStatus);
   }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> tratarExcecao(Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiExceptionDTO apiExceptionDTO = gerarApiExceptionDTO(e, httpStatus, "");
        log.error("Exception - ", e);
        return new ResponseEntity<>(apiExceptionDTO, httpStatus);
    }

    private ApiExceptionDTO gerarApiExceptionDTO(Exception e, HttpStatus httpStatus, String mensagem) {
        if (mostrarMensagemResumida())
            return new ApiExceptionDTO(httpStatus, mensagem);
        else
            return new ApiExceptionDTO(httpStatus, e);
    }

    private boolean mostrarMensagemResumida() {
        return activeProfile.equalsIgnoreCase("prod") ||
               !mostraLogCompleto;
    }
}
