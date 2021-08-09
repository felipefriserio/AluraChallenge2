package br.com.alura.challenge.backend.controllers;

import br.com.alura.challenge.backend.entity.dto.AutenticacaoDto;
import br.com.alura.challenge.backend.entity.dto.form.AutenticacaoForm;
import br.com.alura.challenge.backend.service.AutenticacaoService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    public AutenticacaoController(AutenticacaoService service) {
        this.service = service;
    }

    private AutenticacaoService service;

    @PostMapping
    public ResponseEntity<AutenticacaoDto> gerarToken(@RequestBody @Valid AutenticacaoForm form) {
        String email = form.getEmail();
        String senha = form.getSenha();

        return service.validarCredenciaisEGerarToken(email, senha);
    }
}
