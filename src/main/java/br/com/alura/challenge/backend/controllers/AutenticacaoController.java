package br.com.alura.challenge.backend.controllers;


import br.com.alura.challenge.backend.config.seguranca.TokenAppService;

import br.com.alura.challenge.backend.controllers.dto.ApiExceptionDTO;
import br.com.alura.challenge.backend.controllers.dto.AutenticacaoDTO;
import br.com.alura.challenge.backend.controllers.dto.form.AutenticacaoForm;
import br.com.alura.challenge.backend.config.seguranca.AutenticacaoService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Profile(value = {"prod", "test"})
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    public AutenticacaoController(TokenAppService tokenAppService,
                                  AuthenticationManager authenticationManager) {
        this.tokenAppService = tokenAppService;
        this.authenticationManager = authenticationManager;
    }

    private TokenAppService tokenAppService;
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid AutenticacaoForm form) {
        UsernamePasswordAuthenticationToken login = form.converter();
        Authentication autenticacao = authenticationManager.authenticate(login);
        String token = tokenAppService.gerarToken(autenticacao);

        return ResponseEntity.ok(new AutenticacaoDTO("Bearer", token));
    }

}
