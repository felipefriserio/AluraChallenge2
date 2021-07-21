package br.com.alura.challenge.backend.endpoint;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.VideoDTO;
import br.com.alura.challenge.backend.entity.dto.VideoPaginacaoDTO;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.entity.dto.form.VideoInsertForm;
import br.com.alura.challenge.backend.entity.dto.form.VideoUpdateForm;
import br.com.alura.challenge.backend.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/videos")
public class VideoEndpoint {

    public VideoEndpoint(VideoService service) {
        this.service = service;
    }

    private final VideoService service;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<VideoPaginacaoDTO> listar(VideoFiltro filtro) {
        log.debug("listar - filtro= {}", filtro);
        Page<Video> videos = service.listar(filtro);
        return ResponseEntity.ok(new VideoPaginacaoDTO(videos));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoDTO> pegarUm(@PathVariable("id") Integer id) {
        log.debug("pegarUm - id= {}", id);
        Video video = service.encontrarPorId(id);
        return ResponseEntity.ok(new VideoDTO(video));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<VideoDTO> salvar(@RequestBody @Valid VideoInsertForm form) {
        log.debug("salvar - form= {}", form);
        Video video = form.paraVideo();
        video = service.salvar(video);
        return ResponseEntity.ok(new VideoDTO(video));
    }

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<VideoDTO> atualizar(@RequestBody @Valid VideoUpdateForm form) {
        log.debug("atualizar - form= {}", form);
        Video video = form.paraVideo();
        video = service.atualizar(video);
        return ResponseEntity.ok(new VideoDTO(video));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Integer id) {
        log.debug("deletar - id= {}", id);
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
