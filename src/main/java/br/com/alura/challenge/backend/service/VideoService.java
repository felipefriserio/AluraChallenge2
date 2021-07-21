package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.entity.dto.form.filter.VideoFiltro;
import br.com.alura.challenge.backend.exceptions.VideoNotFoundException;
import br.com.alura.challenge.backend.repository.VideoRepository;
import br.com.alura.challenge.backend.repository.specification.Especificacao;
import br.com.alura.challenge.backend.repository.specification.VideoEspecificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VideoService {

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }

    private VideoRepository repository;

    public Page<Video> listar(VideoFiltro filtro) {
        Especificacao videoEspecificacao = new VideoEspecificacao(filtro);
        Specification queryAnd = videoEspecificacao.queryAnd();

        Pageable paginacao = filtro.getPaginacao();
        Page<Video> pagDeVideos = repository.findAll(queryAnd, paginacao);

        return pagDeVideos;
    }

    public Video encontrarPorId(Integer id) {
        Optional<Video> videoOptional = repository.findById(id);
        if (videoOptional.isPresent())
            return videoOptional.get();
        else
            throw new VideoNotFoundException("Video com id= "+ id + " nao encontrado.");
    }

    @Transactional
    public Video salvar(Video video) {
        return repository.save(video);
    }

    @Transactional
    public Video atualizar(Video videoAtualizado){
        Integer id = videoAtualizado.getId();

        Video video = encontrarPorId(id);
        video.setTitulo(videoAtualizado.getTitulo());
        video.setDescricao(videoAtualizado.getDescricao());
        video.setUrl(videoAtualizado.getUrl());

        return video;
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}
