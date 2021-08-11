package br.com.alura.challenge.backend.config;

import br.com.alura.challenge.backend.controllers.dto.ApiExceptionDTO;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String mensagem = "Credenciais inválidas";
        ApiExceptionDTO dto = new ApiExceptionDTO(status, mensagem);

        JSONObject jsonObject = new JSONObject(dto);

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(status.value());
        res.getWriter().write(jsonObject.toString());
    }
}