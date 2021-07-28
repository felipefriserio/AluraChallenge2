package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.repository.VideoRepository;
import br.com.alura.challenge.backend.repository.specification.Especificacao;
import br.com.alura.challenge.backend.repository.specification.VideoEspecificacao;
import br.com.alura.challenge.backend.service.validacoes.video.incluir.ValidacaoParaIncluirVideo;
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
                        List<ValidacaoParaIncluirVideo> listaDeValidacoesParaIncluir) {
        this.repository = repository;
        this.listaDeValidacoesParaIncluir = listaDeValidacoesParaIncluir;
    }

    private List<ValidacaoParaIncluirVideo> listaDeValidacoesParaIncluir;
    private VideoRepository repository;

    public Page<Video> listar(VideoFiltro filtro) {
        Especificacao videoEspecificacao = new VideoEspecificacao(filtro);
        Specification queryAnd = videoEspecificacao.queryAnd();

        Pageable paginacao = filtro.getPaginacao();
        Page<Video> paginaDeVideos = repository.findAll(queryAnd, paginacao);

        return paginaDeVideos;
    }

    public Video encontrarPorId(Long id) {
        Optional<Video> videoOptional = repository.findById(id);
        if (videoOptional.isPresent())
            return videoOptional.get();
        else
            throw new EntidadeNaoEncontradaException("VideoId= "+ id + " nao encontrado");
    }

    @Transactional
    public Video salvar(Video video) {
        for (ValidacaoParaIncluirVideo validacao : listaDeValidacoesParaIncluir) {
            validacao.validar(video);
        }
        return repository.save(video);
    }

    @Transactional
    public Video atualizar(Video videoAtualizado){
        Long id = videoAtualizado.getId();

        Video video = encontrarPorId(id);
        video.setTitulo(videoAtualizado.getTitulo());
        video.setDescricao(videoAtualizado.getDescricao());
        video.setUrl(videoAtualizado.getUrl());

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
        Collections.sort(listaDeValidacoesParaIncluir,
                AnnotationAwareOrderComparator.INSTANCE);
    }
}
