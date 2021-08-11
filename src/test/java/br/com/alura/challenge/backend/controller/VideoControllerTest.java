package br.com.alura.challenge.backend.controller;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.controllers.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.service.VideoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService service;

    @Test
    void deveRetornar200_AoListar() throws Exception {
        Page<Video> videosPaginados = mockarVideosPaginados();
        when(service.listar(any(VideoFiltro.class)))
               .thenReturn(videosPaginados);

        mockMvc.perform(get("/videos"))
               .andDo(print())
               .andExpect(status().isOk());

        verify(service, times(1)).listar(any());
    }

    @Test
    void deveRetornar200_EncontrarPorId() throws Exception {
        Long id = 1l;
        Video video = mockarUmVideo();
        when(service.encontrarPorId(id))
               .thenReturn(video);

        mockMvc.perform(get("/videos/{id}", id))
               .andDo(print())
               .andExpect(status().isOk());

        verify(service, times(1)).encontrarPorId(any());

    }

    @Test
    void deveRetornar404_EncontrarVideoInexistente() throws Exception {
        Long id = -1l;
        Throwable throwable = new EntidadeNaoEncontradaException("VideoId= "+ id +" nao encontrado");
        when(service.encontrarPorId(id))
               .thenThrow(throwable);

        mockMvc.perform(get("/videos/{id}", id))
               .andDo(print())
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.httpStatusCodigo", is(404)))
               .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
               .andExpect(jsonPath("$.mensagem", containsString("VideoId= "+ id +" nao encontrado")));

        verify(service, times(1)).encontrarPorId(any());
    }

    @Test
    void deveRetornar201_SalvarUmVideo() throws Exception {
        Video video = mockarUmVideo();
        String json = toJson(video);

        when(service.salvar(any(Video.class))).thenReturn(video);

        mockMvc.perform(MockMvcRequestBuilders.post("/videos")
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andDo(print());

        verify(service, times(1)).salvar(any(Video.class));
    }

    @Test
    void deveRetornar400_SalvarUmVideoSemTitulo() throws Exception {
        Video video = mockarUmVideo();
        video.setTitulo("");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.post("/videos")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("titulo obrigatorio")));

        verify(service, times(0)).salvar(any(Video.class));
    }

    @Test
    void deveRetornar400_SalvarUmVideoSemDescricao() throws Exception {
        Video video = mockarUmVideo();
        video.setDescricao("");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.post("/videos")
                   .accept(MediaType.APPLICATION_JSON)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(json))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
               .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
               .andExpect(jsonPath("$.mensagem", containsString("descricao obrigatorio")));

        verify(service, times(0)).salvar(any(Video.class));
    }

    @Test
    void deveRetornar400_SalvarUmVideoSemUrl() throws Exception {
        Video video = mockarUmVideo();
        video.setUrl("");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.post("/videos")
                   .accept(MediaType.APPLICATION_JSON)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(json))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
               .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
               .andExpect(jsonPath("$.mensagem", containsString("url obrigatorio")));

        verify(service, times(0)).salvar(any(Video.class));
    }

    @Test
    void deveRetornar400_SalvarUmVideoComUrlInvalida() throws Exception {
        Video video = mockarUmVideo();
        video.setUrl("url invalida");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.post("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("url invalida")));

        verify(service, times(0)).salvar(any(Video.class));
    }

    @Test
    void deveRetornar200_AposAtualizarUmVideo() throws Exception {
        Video video = mockarUmVideo();
        String json = toJson(video);

        when(service.atualizar(any(Video.class))).thenReturn(video);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
               .andDo(print())
               .andExpect(status().isOk());

        verify(service, times(1)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar400_AtualizarUmVideoSemId() throws Exception {
        Video video = mockarUmVideo();
        video.setId(null);
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("id obrigatorio")));

        verify(service, times(0)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar400_AtualizarUmVideoSemTitulo() throws Exception {
        Video video = mockarUmVideo();
        video.setTitulo("");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("titulo obrigatorio")));

        verify(service, times(0)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar400_AtualizarUmVideoSemDescricao() throws Exception {
        Video video = mockarUmVideo();
        video.setDescricao("");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("descricao obrigatorio")));

        verify(service, times(0)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar400_AtualizarUmVideoSemUrl() throws Exception {
        Video video = mockarUmVideo();
        video.setUrl("");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("url obrigatorio")));

        verify(service, times(0)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar400_AtualizarUmVideoComUrlInvalida() throws Exception {
        Video video = mockarUmVideo();
        video.setUrl("url invalida");
        String json = toJson(video);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCodigo", is(400)))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.mensagem", containsString("url invalida")));

        verify(service, times(0)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar404_AposTentarAtualizarVideoInexistente() throws Exception {
        Throwable throwable = new EntidadeNaoEncontradaException("VideoId= "+ -1l + " nao encontrado");
        Video video = mockarUmVideo();
        String json = toJson(video);

        when(service.atualizar(any(Video.class)))
            .thenThrow(throwable);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service, times(1)).atualizar(any(Video.class));
    }

    @Test
    void deveRetornar200_AposDeletarUmVideo() throws Exception {
        Long id = 1l;
        mockMvc.perform(MockMvcRequestBuilders.delete("/videos/{id}", id))
                    .andDo(print())
                    .andExpect(status().isOk());

        verify(service, times(1)).deletar(any(Long.class));
    }

    @Test
    void deveRetornar404_AposTentarDeletarUmVideoInexistente() throws Exception {
        Long id = -1l;
        Throwable throwable = new EntidadeNaoEncontradaException("VideoId= "+ id + " nao encontrado");

        doThrow(throwable).when(service).deletar(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/videos/{id}", id))
               .andDo(print())
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.httpStatusCodigo", is(404)))
               .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")))
               .andExpect(jsonPath("$.mensagem", containsString("VideoId= " + id + " nao encontrado")));

        verify(service, times(1)).deletar(any(Long.class));
    }

    private Page<Video> mockarVideosPaginados() {
        Video video = mockarUmVideo();
        List<Video> videos = Arrays.asList(video);
        Page<Video> videosPaginados = new PageImpl<>(videos);
        return videosPaginados;
    }

    private Video mockarUmVideo() {
        Categoria categoria = new Categoria(1l,"LIVRE", "#000000");
        Video video = new Video(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
