package br.com.alura.challenge.backend.controller;

import br.com.alura.challenge.backend.controllers.CategoriaController;
import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.form.filter.CategoriaFiltro;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.service.CategoriaService;
import br.com.alura.challenge.backend.service.VideoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService service;

    @MockBean
    private VideoService videoService;


    @Test
    void deveRetornar200_AoListar() throws Exception {
        Page<Categoria> categoriasPaginadas = mockarCategoriasPaginadas();
        when(service.listar(any(CategoriaFiltro.class)))
                    .thenReturn(categoriasPaginadas);

        mockMvc.perform(get("/categorias"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).listar(any());
    }

    @Test
    void deveRetornar200_AoListarVideosPorCategoria() throws Exception {
        Long categoriaId = 1l;
        Page<Video> videosPaginados = mockarVideosPaginados();
        when(videoService.listar(any(VideoFiltro.class)))
                         .thenReturn(videosPaginados);

        mockMvc.perform(get("/categorias/{categoriaId}/videos", categoriaId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(videoService, times(1)).listar(any());
    }

    @Test
    void deveRetornar200_EncontrarPorId() throws Exception {
        Long id = 1l;
        Categoria categoria = mockarUmaCategoria();
        when(service.encontrarPorId(id))
                    .thenReturn(categoria);

        mockMvc.perform(get("/categorias/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).encontrarPorId(any());
    }

    @Test
    void deveRetornar404_EncontrarCategoriaInexistente() throws Exception {
        Long id = -1l;
        Throwable throwable = new EntidadeNaoEncontradaException("CategoriaId " + id + " nao encontrado");
        when(service.encontrarPorId(id))
                    .thenThrow(throwable);

        mockMvc.perform(get("/categorias/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatusCodigo", is(404)))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.mensagem", containsString("CategoriaId " + id + " nao encontrado")));

        verify(service, times(1)).encontrarPorId(any());
    }

    @Test
    void deveRetornar201_SalvarUmaCategoria() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        String json = toJson(categoria);

        when(service.salvar(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        verify(service, times(1)).salvar(any(Categoria.class));
    }

    @Test
    void deveRetornar400_SalvarUmaCategoriaSemTitulo() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        categoria.setTitulo("");
        String json = toJson(categoria);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("titulo obrigatorio")));

        verify(service, times(0)).salvar(any(Categoria.class));
    }

    @Test
    void deveRetornar400_SalvarUmaCategoriaSemCor() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        categoria.setCor("");
        String json = toJson(categoria);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("cor obrigatoria")));

        verify(service, times(0)).salvar(any(Categoria.class));
    }

    @Test
    void deveRetornar200_AposAtualizar() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        String json = toJson(categoria);

        when(service.atualizar(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).atualizar(any(Categoria.class));
    }

    @Test
    void deveRetornar404_AposTentarAtualizarCategoriaInexistente() throws Exception {
        Long id = -1l;
        Throwable throwable = new EntidadeNaoEncontradaException("CategoriaId " + id + " nao encontrado");
        Categoria categoria = mockarUmaCategoria();
        categoria.setId(id);
        String json = toJson(categoria);

        when(service.atualizar(any(Categoria.class)))
                    .thenThrow(throwable);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service, times(1)).atualizar(any(Categoria.class));
    }

    @Test
    void deveRetornar400_AtualizarUmaCategoriaSemId() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        categoria.setId(null);
        String json = toJson(categoria);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("id obrigatorio")));

        verify(service, times(0)).atualizar(any(Categoria.class));
    }

    @Test
    void deveRetornar400_AtualizarUmaCategoriaSemTitulo() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        categoria.setTitulo(null);
        String json = toJson(categoria);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("titulo obrigatorio")));

        verify(service, times(0)).atualizar(any(Categoria.class));
    }

    @Test
    void deveRetornar400_AtualizarUmaCategoriaSemCor() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        categoria.setCor(null);
        String json = toJson(categoria);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("cor obrigatoria")));

        verify(service, times(0)).atualizar(any(Categoria.class));
    }

    @Test
    void deveRetornar200_AposDeletarUmaCategoria() throws Exception {
        Long id = 1l;
        mockMvc.perform(MockMvcRequestBuilders.delete("/categorias/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).deletar(any(Long.class));
    }

    @Test
    void deveRetornar404_AposTentarDeletarUmaCategoriaInexistente() throws Exception {
        Long id = -1l;
        Throwable throwable = new EntidadeNaoEncontradaException("CategoriaId " + id + " nao encontrado");
        doThrow(throwable).when(service).deletar(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/categorias/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatusCodigo", is(404)))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
                .andExpect(jsonPath("$.mensagem", containsString("CategoriaId " + id + " nao encontrado")));

        verify(service, times(1)).deletar(any(Long.class));
    }

    private Page<Categoria> mockarCategoriasPaginadas() {
        Categoria categoria = mockarUmaCategoria();
        List<Categoria> categorias = Arrays.asList(categoria);
        return new PageImpl<>(categorias);
    }

    private Page<Video> mockarVideosPaginados() {
        Video video = mockarUmVideo();
        List<Video> videos = Arrays.asList(video);
        Page<Video> videosPaginados = new PageImpl<>(videos);
        return videosPaginados;
    }

    private Categoria mockarUmaCategoria() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        return categoria;
    }

    private Video mockarUmVideo() {
        Categoria categoria = mockarUmaCategoria();
        Video video = new Video(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
