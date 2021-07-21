package br.com.alura.challenge.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiException {

    public ApiException(Exception e,
                        HttpStatus httpStatus,
                        boolean deveMostrarStackTrace){
        this.httpStatus = httpStatus;
        this.httpStatusCodigo = httpStatus.value();

        if (deveMostrarStackTrace) {
            this.mensagem = e.getMessage();
            this.stackTrace = stackTraceToString(e);
        }
    }

    @JsonProperty(value = "httpstatuscodigo")
    private final int httpStatusCodigo;

    @JsonProperty(value = "httpstatus")
    private final HttpStatus httpStatus;

    @JsonProperty(value = "mensagem")
    private String mensagem;

    @JsonProperty(value = "data")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime data = LocalDateTime.now();

    @JsonProperty(value = "stacktrace")
    private String stackTrace = null;

    private String stackTraceToString(Exception e) {
        return ExceptionUtils.getStackTrace(e);
    }
}
