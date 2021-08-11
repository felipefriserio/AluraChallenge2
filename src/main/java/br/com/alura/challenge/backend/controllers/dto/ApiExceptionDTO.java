package br.com.alura.challenge.backend.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiExceptionDTO {

    public ApiExceptionDTO(HttpStatus httpStatus,
                           String mensagem) {
        this.httpStatus = httpStatus;
        this.httpStatusCodigo = httpStatus.value();
        this.mensagem = mensagem;
    }

    public ApiExceptionDTO(HttpStatus httpStatus,
                           Exception e){
        this.httpStatus = httpStatus;
        this.httpStatusCodigo = httpStatus.value();
        this.mensagem = e.getMessage();
        this.stackTrace = stackTraceToString(e);
    }

    @JsonProperty(value = "httpStatusCodigo")
    private final int httpStatusCodigo;

    @JsonProperty(value = "httpStatus")
    private final HttpStatus httpStatus;

    @JsonProperty(value = "mensagem")
    private String mensagem;

    @JsonProperty(value = "data")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private final LocalDateTime data = LocalDateTime.now();

    @JsonProperty(value = "stackTrace")
    private String stackTrace = null;

    private String stackTraceToString(Exception e) {
        return ExceptionUtils.getStackTrace(e);
    }
}
