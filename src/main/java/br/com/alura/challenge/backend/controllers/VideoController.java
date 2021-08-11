package br.com.alura.challenge.backend.controllers;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.controllers.dto.RelatorioFilmesPorCategoriaDTO;
import br.com.alura.challenge.backend.controllers.dto.VideoDTO;
import br.com.alura.challenge.backend.controllers.dto.paginacao.VideoPaginacaoDTO;
import br.com.alura.challenge.backend.controllers.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.controllers.dto.form.VideoInsertForm;
import br.com.alura.challenge.backend.controllers.dto.form.VideoUpdateForm;
import br.com.alura.challenge.backend.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/videos")
public class VideoController {

    public VideoController(VideoService service) {
        this.service = service;
    }

    private final VideoService service;

    @GetMapping
    public ResponseEntity<VideoPaginacaoDTO> listar(VideoFiltro filtro) {
        log.info("VideoController.listar - filtro= {}", filtro);
        Page<Video> videos = service.listar(filtro);
        return ResponseEntity.ok(new VideoPaginacaoDTO(videos));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoDTO> pegarUm(@PathVariable("id") Long id) {
        log.debug("VideoController.pegarUm - id= {}", id);
        Video video = service.encontrarPorId(id);
        return ResponseEntity.ok(new VideoDTO(video));
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<VideoDTO>> listarFilmesGratuitos() {
        log.debug("VideoController.listarFilmesGratuitos");
        List<Video> videosGratuitos = service.listarFilmesGratuitos();
        return ResponseEntity.ok(new VideoDTO().listarVideos(videosGratuitos));
    }

    @PostMapping
    public ResponseEntity<VideoDTO> salvar(@RequestBody @Valid VideoInsertForm form, UriComponentsBuilder uriBuilder) {
        log.debug("VideoController.salvar - form= {}", form);
        Video video = form.paraVideo();
        video = service.salvar(video);

        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoDTO(video));
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<VideoDTO> atualizar(@RequestBody @Valid VideoUpdateForm form) {
        log.debug("VideoController.atualizar - form= {}", form);
        Video video = form.paraVideo();
        video = service.atualizar(video);
        return ResponseEntity.ok(new VideoDTO(video));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<VideoDTO> deletar(@PathVariable("id") Long id) {
        log.debug("VideoController.deletar - id= {}", id);
        service.deletar(id);
        String mensagem = "Video id=" + id + " deletado com sucesso";
        return ResponseEntity.ok(new VideoDTO(mensagem));
    }
}
