package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.repository.VideoRepository;
import br.com.alura.challenge.backend.repository.specification.Especificacao;
import br.com.alura.challenge.backend.repository.specification.VideoEspecificacao;
import br.com.alura.challenge.backend.service.validacoes.video.ValidacaoVideo;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    public VideoService(VideoRepository repository,
                        List<ValidacaoVideo> listaDeValidacoes) {
        this.repository = repository;
        this.listaDeValidacoes = listaDeValidacoes;
    }

    private VideoRepository repository;
    private List<ValidacaoVideo> listaDeValidacoes;


    public Page<Video> listar(VideoFiltro filtro) {
        Especificacao videoEspecificacao = new VideoEspecificacao(filtro);
        Specification queryAnd = videoEspecificacao.queryAnd();

        Pageable paginacao = filtro.getPaginacao();
        return repository.findAll(queryAnd, paginacao);
    }

    public Video encontrarPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("id nulo");

        Optional<Video> videoOptional = repository.findById(id);
        if (videoOptional.isPresent())
            return videoOptional.get();
        else
            throw new EntidadeNaoEncontradaException("VideoId= "+ id + " nao encontrado");
    }

    public List<Video> listarFilmesGratuitos(){
        final int QUANTIDADE_FILMES_LIBERADOS = 2;
        VideoFiltro videoFiltro = new VideoFiltro();
        videoFiltro.setQuantidadeDeItensPorPagina(QUANTIDADE_FILMES_LIBERADOS);
        videoFiltro.setPagina(0);
        Page<Video> paginacao = listar(videoFiltro);
        return paginacao.getContent();
    }

    @Transactional
    public Video salvar(Video video) {
        for (ValidacaoVideo validacao : listaDeValidacoes) {
            validacao.validar(video);
        }
        return repository.save(video);
    }

    @Transactional
    public Video atualizar(Video videoAtualizado) {
        if (videoAtualizado == null) throw new IllegalArgumentException("video nulo");

        for (ValidacaoVideo validacao : listaDeValidacoes) {
            validacao.validar(videoAtualizado);
        }

        Long id = videoAtualizado.getId();

        Video video = encontrarPorId(id);
        video.setTitulo(videoAtualizado.getTitulo());
        video.setDescricao(videoAtualizado.getDescricao());
        video.setUrl(videoAtualizado.getUrl());
        video.setCategoria(videoAtualizado.getCategoria());

        return video;
    }

    public void deletar(Long id) {
        Video video = encontrarPorId(id);
        deletar(video);
    }

    public void deletar(Video video) {
        repository.delete(video);
    }

    @PostConstruct
    private void ordenarListaDeValidacoesParaExecucao() {
        Collections.sort(listaDeValidacoes, AnnotationAwareOrderComparator.INSTANCE);
     }
}
