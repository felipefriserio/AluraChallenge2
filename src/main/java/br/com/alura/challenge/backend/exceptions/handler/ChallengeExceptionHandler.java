package br.com.alura.challenge.backend.exceptions.handler;

import br.com.alura.challenge.backend.exceptions.ApiException;
import br.com.alura.challenge.backend.exceptions.VideoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ChallengeExceptionHandler {

    @Value("${log.stacktrace:false}")
    private boolean deveMostrarStackTrace;

    @ExceptionHandler(value = {VideoNotFoundException.class})
    public ResponseEntity<Object> tratarExcecao(VideoNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e, httpStatus, deveMostrarStackTrace);
        log.error("VideoNotFoundException - ", e);
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> tratarExcecao(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e, httpStatus, deveMostrarStackTrace);
        log.error("MethodArgumentNotValidException - ", e);
        return new ResponseEntity<>(apiException, httpStatus);
    }

   @ExceptionHandler(value = {RuntimeException.class})
   public ResponseEntity<Object> tratarExcecao(RuntimeException e) {
       HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
       ApiException apiException = new ApiException(e, internalServerError, deveMostrarStackTrace);
       log.error("RuntimeException - ", e);
       return new ResponseEntity<>(apiException, internalServerError);
   }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> tratarExcecao(Exception e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(e, internalServerError, deveMostrarStackTrace);
        log.error("Exception - ", e);
        return new ResponseEntity<>(apiException, internalServerError);
    }
}
