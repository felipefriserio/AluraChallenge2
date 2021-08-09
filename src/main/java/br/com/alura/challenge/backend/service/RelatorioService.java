package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.dto.RelatorioFilmesPorCategoriaDTO;
import br.com.alura.challenge.backend.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    public RelatorioService(VideoRepository repository) {
        this.repository = repository;
    }

    private VideoRepository repository;

    public List<RelatorioFilmesPorCategoriaDTO> gerarRelatorioFilmePorCategoria() {
        return repository.relatorioDeFilmesPorCategoria();
    }
}
