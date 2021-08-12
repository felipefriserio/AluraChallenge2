package br.com.alura.challenge.backend.controllers;

import br.com.alura.challenge.backend.controllers.dto.RelatorioFilmesPorCategoriaDTO;
import br.com.alura.challenge.backend.service.RelatorioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/relatorios")
public class RelatorioContoller {

    public RelatorioContoller(RelatorioService service) {
        this.service = service;
    }

    private RelatorioService service;

    @CrossOrigin
    @GetMapping(value = "/categorias")
    public ResponseEntity<List<RelatorioFilmesPorCategoriaDTO>> gerarRelatorio() {
        log.debug("RelatorioContoller.gerarRelatorio");
        List<RelatorioFilmesPorCategoriaDTO> relatorio = service.gerarRelatorioFilmePorCategoria();
        return ResponseEntity.ok(relatorio);
    }
}
