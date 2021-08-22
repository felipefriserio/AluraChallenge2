package br.com.alura.challenge.backend.controllers;

import br.com.alura.challenge.backend.config.seguranca.TokenAppService;
import br.com.alura.challenge.backend.controllers.dto.AutenticacaoDTO;
import br.com.alura.challenge.backend.controllers.dto.form.AutenticacaoForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
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
        log.debug("AutenticacaoController.autenticar");
        UsernamePasswordAuthenticationToken login = form.converter();
        Authentication autenticacao = authenticationManager.authenticate(login);
        String token = tokenAppService.gerarToken(autenticacao);

        return ResponseEntity.ok(new AutenticacaoDTO("Bearer", token));
    }
}
