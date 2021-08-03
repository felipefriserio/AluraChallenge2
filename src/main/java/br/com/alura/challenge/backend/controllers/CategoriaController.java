package br.com.alura.challenge.backend.controllers;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.CategoriaDTO;
import br.com.alura.challenge.backend.entity.dto.VideoDTO;
import br.com.alura.challenge.backend.entity.dto.form.CategoriaInsertForm;
import br.com.alura.challenge.backend.entity.dto.form.CategoriaUpdateForm;
import br.com.alura.challenge.backend.entity.dto.form.filter.CategoriaFiltro;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.entity.dto.paginacao.CategoriaPaginacaoDTO;
import br.com.alura.challenge.backend.entity.dto.paginacao.VideoPaginacaoDTO;
import br.com.alura.challenge.backend.service.CategoriaService;
import br.com.alura.challenge.backend.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    public CategoriaController(CategoriaService service,
                               VideoService videoService) {
        this.service = service;
        this.videoService = videoService;
    }

    private CategoriaService service;
    private VideoService videoService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<CategoriaPaginacaoDTO> listar(CategoriaFiltro filtro) {
        log.debug("CategoriaController.listar - filtro= {}", filtro);
        Page<Categoria> categorias = service.listar(filtro);
        return ResponseEntity.ok(new CategoriaPaginacaoDTO(categorias));
    }

    @CrossOrigin
    @GetMapping("/{categoriaId}/videos")
    public ResponseEntity<VideoPaginacaoDTO> listarVideosPorCategoria(@PathVariable("categoriaId") Long categoriaId) {
        log.debug("VideoController.listarVideosPorCategoria - categoriaId= {}", categoriaId);
        VideoFiltro filtro = new VideoFiltro(categoriaId);
        Page<Video> videos = videoService.listar(filtro);
        return ResponseEntity.ok(new VideoPaginacaoDTO(videos));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> pegarUma(@PathVariable("id") Long id) {
        log.debug("CategoriaController.pegarUm - id= {}", id);
        Categoria categoria = service.encontrarPorId(id);
        return ResponseEntity.ok(new CategoriaDTO(categoria));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<CategoriaDTO> salvar(@RequestBody @Valid CategoriaInsertForm form, UriComponentsBuilder uriBuilder) {
        log.debug("CategoriaController.salvar - form= {}", form);
        Categoria categoria = form.paraCategoria();
        categoria = service.salvar(categoria);

        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaDTO(categoria));
    }

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<CategoriaDTO> atualizar(@RequestBody @Valid CategoriaUpdateForm form) {
        log.debug("CategoriaController.atualizar - form= {}", form);
        Categoria categoria = form.paraCategoria();
        categoria = service.atualizar(categoria);
        return ResponseEntity.ok(new CategoriaDTO(categoria));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> deletar(@PathVariable("id") Long id) {
        log.debug("CategoriaController.deletar - id= {}", id);
        service.deletar(id);
        String mensagem = "Categoria id=" + id + " deletada com sucesso";
        return ResponseEntity.ok(new CategoriaDTO(mensagem));
    }
}
